(set-env! :dependencies '[[edu.ucdenver.ccp/kabob "2.0.0-BG"]
                          [org.clojure/java.classpath "0.3.0"]])
(require '[clojure.java.io :refer [file]]
         '[clojure.string :refer [trim-newline]]
         '[kabob.build.run-rules :refer [commandline-process-forward-rules]]
         '[kabob.build.input-kb :refer [open-kb]]
         '[kabob.build.id-sets.generate :refer [generate-all-id-sets]]
         '[kr.core.sparql :refer [visit-sparql query-sparql]]
         '[kr.core.kb :refer [connection]]
         '[clojure.java.classpath :refer [classpath-jarfiles]])
(import java.io.File
        java.io.BufferedReader
        java.io.FileReader
        com.bigdata.rdf.sail.ExportKB
        org.openrdf.query.QueryLanguage
        org.openrdf.repository.RepositoryConnection)


;; utility tasks
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(deftask query
         "Submit a query the kb. Note that results will be printed to the console, so using LIMIT is recommended."
         [a allegrograph bool "Use an Allegrograph back end"
          b blazegraph bool "Use a Blazegraph back end"
          s stardog bool "Use a Stardog back end"
          n kb-name VAL str "KB name"
          l server-url VAL str "Server URL"
          u username VAL str "Server username"
          p password VAL str "Server password"
          q query VAL str "query to submit. Use either query or query-file, but not both arguments."
          f query-file VAL str "file containing the query to submit. Use either query or query-file, but not both arguments."]
         (with-pre-wrap fileset
                        ;; ensure that only one of the server back ends has been selected, otherwise error
                        (if (not= 1 (+ (if allegrograph 1 0)
                                       (if blazegraph 1 0)
                                       (if stardog 1 0)))
                          (throw (IllegalArgumentException.
                                   "More than one server implementation has been requested. Please select only one.")) true)
                        (try
                          (let [
                                params {:repo-name   kb-name
                                        :username    username
                                        :password    password
                                        :server-url  server-url
                                        :server-impl (cond
                                                       allegrograph "allegrograph"
                                                       blazegraph "blazegraph"
                                                       stardog "stardog")}
                                kb (open-kb params)
                                bindings-fn (fn [bindings]
                                              (doall (map (fn [x] (print (str x " "))) bindings))
                                              (println))]
                            (println (str "params: " params))
                            (if (not (nil? query-file))
                              (visit-sparql kb bindings-fn (slurp query-file))
                              (visit-sparql kb bindings-fn query)))
                          ;(doall (map (fn [x] (println (str x))) (query-sparql kb query))))
                          (catch Exception e (println "Caught Exception while querying: ")
                                             (.printStackTrace e)
                                             (println (str "Message: " (.getMessage e)))))
                        fileset))





(deftask show-classpath-classes []
         "output a list of all classes and their corresponding jar file to the console"
         (let [jarfiles (classpath-jarfiles)]
           (println (str "jarfile count: " (count jarfiles)))
           (doall (map (fn [x] (let [jar-name (.getName x)
                                     entries (.entries x)]
                                 (while (.hasMoreElements entries)
                                   (println (str jar-name " -- " (.getName (.nextElement entries)))))))
                       jarfiles))))


;; KaBOB build tasks
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn allegrograph-docker-params []
  "define parameters specific to the Allegrograph implementation"
  (let [load-request-dir (file "/kabob-load-requests")
        container-name (trim-newline (slurp (file load-request-dir "agraph.container.name")))
        kb-port (trim-newline (slurp (file load-request-dir "agraph.port")))]
    {:kb-url              (str "http://" container-name ":" kb-port)
     :kb-user             "test"
     :kb-password         "xyzzy"
     :kb-data-directory   "/kabob_data/agraph-build"
     :kb-load-request-dir load-request-dir
     :kb-port             kb-port}))

(defn blazegraph-docker-params []
  "define parameters specific to the Blazegraph implementation"
  (let [load-request-dir (file "/kabob-load-requests")
        container-name (trim-newline (slurp (file load-request-dir "blazegraph.container.name")))
        kb-port (trim-newline (slurp (file load-request-dir "blazegraph.port")))]
    {:kb-url              (str "http://" container-name ":" kb-port "/blazegraph")
     :kb-user             "admin"
     :kb-password         "admin"
     :kb-data-directory   "/kabob_data/blazegraph-build"
     :kb-load-request-dir load-request-dir
     :kb-port             kb-port}))

(defn stardog-docker-params []
  "define parameters specific to the Stardog implementation"
  (let [load-request-dir (file "/kabob-load-requests")
        container-name (trim-newline (slurp (file load-request-dir "stardog.container.name")))
        kb-port (trim-newline (slurp (file load-request-dir "stardog.port")))]
    {:kb-url              (str "http://" container-name ":" kb-port)
     :kb-user             "admin"
     :kb-password         "admin"
     :kb-data-directory   "/kabob_data/stardog-build"
     :kb-load-request-dir load-request-dir
     :kb-port             kb-port}))

(deftask server
         "specify the triple store implementation that will be used and the name of the repository"
         [a allegrograph bool "Use an Allegrograph back end"
          b blazegraph bool "Use a Blazegraph back end"
          s stardog bool "Use a Stardog back end"
          n kb-name VAL str "KB name"
          d docker bool "if set, server expects docker environment and uses *-docker-params functions to assign server params"
          l server-url VAL str "Server URL. Optional, set if not setting --docker."
          u username VAL str "Server username. Optional, set if not setting --docker."
          p password VAL str "Server password. Optional, set if not setting --docker."
          y data-directory VAL str "Directory where data from KaBOB build will be stored. Optional, typically used for testing locally."
          o ontology-directory VAL str "Directory where the ontologies that will be loaded into KaBOB are located."
          i ice-directory VAL str "Directory where the ICE RDF files that will be loaded into KaBOB are located."]
         (with-pre-wrap fileset
                        ;; ensure that only one of the server back ends has been selected, otherwise error
                        (if (not= 1 (+ (if allegrograph 1 0)
                                       (if blazegraph 1 0)
                                       (if stardog 1 0)))
                          (throw (IllegalArgumentException.
                                   "Zero or more than one server implementation has been requested. Please select only one.")))

                        ;; make sure that either the docker flag was set, or a URL, user, and password were provided
                        (if (and (not docker)
                                 (or (nil? server-url)
                                     (nil? username)
                                     (nil? password)))
                          (throw (IllegalArgumentException.
                                   "No server connection parameters provided, please select either the 'docker' flag or provide an explicit server URL, username, and password.")))

                        ; add the kb-name and the server-implementation to the fileset and initialize server-specific parameters
                        (let [fs (merge fileset {:kb-name        kb-name
                                                 :kb-server-impl (cond
                                                                   allegrograph :allegrograph
                                                                   blazegraph :blazegraph
                                                                   stardog :stardog)}
                                        (if docker (merge {:kb-ontology-directory "/kabob_data/ontology"
                                                           :kb-ice-directory      "/kabob_data/rdf"}
                                                          (cond
                                                            allegrograph (allegrograph-docker-params)
                                                            blazegraph (blazegraph-docker-params)
                                                            stardog (stardog-docker-params)))
                                                   {:kb-url                server-url
                                                    :kb-user               username
                                                    :kb-password           password
                                                    :kb-data-directory     data-directory
                                                    :kb-ontology-directory ontology-directory
                                                    :kb-ice-directory      ice-directory}))
                              updated-fs (merge fs {:id-set-directory (str (.getAbsolutePath (file (:kb-data-directory fs) (:kb-name fs) "id-sets")) File/separator)})]
                          updated-fs)))


(defn run-update [server-params query]
  "perform the actual update"
  (let [connection ^RepositoryConnection (:connection (connection (open-kb server-params)))
        update (.prepareUpdate connection
                               QueryLanguage/SPARQL
                               query)]
    (println (str "update: " query))
    (.execute update)
    (.close connection)))

(deftask update-kb
         "Update the kb"
         [q query VAL str "update to submit"]
         (with-pre-wrap fileset
                        (let [params {:repo-name   (:kb-name fileset)
                                      :username    (:kb-user fileset)
                                      :password    (:kb-password fileset)
                                      :server-url  (:kb-url fileset)
                                      :server-impl (:kb-server-impl fileset)}]
                          (run-update params query))
                        fileset))


(defn init-stardog-kb []
  "Stardog requires explicit creation of a KB before it is populated. This method
  submits a file to the Stardog load directory which causes a new KB to be initialized."
  (throw (UnsupportedOperationException. "The init-stardog-kb function has not been implemented.")))


(deftask init-kb []
         "initializes a new KB (if necessary)"
         (with-pre-wrap fileset
                        (println "Initializing the KB...")
                        (let [server-impl (:kb-server-impl fileset)]
                          (cond
                            (= server-impl :blazegraph) :default
                            (= server-impl :allegrograph) :default
                            (= server-impl :stardog) (init-stardog-kb)
                            :else (throw (IllegalArgumentException.
                                           (str "Unhandled server implementation: " server-impl))))
                          fileset)))



(defn backup-kb-bg [kb-url backup-label]
  "prompt a Blazegraph KB to generate a backup"
  (println (str "Backing up Blazegraph repository"))
  (dosh "scripts/docker/blazegraph-specific/backup.sh"
        "-u" kb-url
        "-l" backup-label))

(deftask backup-kb
         "prompt the kb to create a backup"
         [l backup-label VAL str "label that will be appended to the backup file name to uniquely identify it."]

         (with-pre-wrap fileset
                        (println (str "Backing up KB. URL = " (:kb-url fileset) " LABEL = " backup-label))
                        (let [server-impl (:kb-server-impl fileset)]
                          (cond
                            (= server-impl :blazegraph) (backup-kb-bg (:kb-url fileset) backup-label)
                            (= server-impl :allegrograph) (println "WARNING - backup for the Allegrograph backend is not yet implemented.")
                            (= server-impl :stardog) (println "WARNING - backup for the Stardog backend is not yet implemented.")
                            :else (throw (IllegalArgumentException.
                                           (str "Unhandled server implementation: " server-impl))))
                          fileset)))


(deftask rule
         "Specify a rule to process"
         [p path-to-rule VAL str "path to rule file"]
         ;; eventually would be nice to add an argument to specify classpath or local path for rule location
         ;; and to be able to process single rules
         ;;l localpath       bool "rule path is a local path (by default the path is treated as the classpath)"]
         (fn middleware [next-handler]
           (fn handler [fileset]
             (next-handler (merge fileset {:path-to-rule path-to-rule})))))

(deftask print-rule []
         "prints the active rule"
         (with-pre-wrap fileset
                        (println (str "Active rule: " (:path-to-rule fileset)))
                        fileset))

(deftask run-rule []
         "Run a forward chaining rule"
         (with-pre-wrap fileset
                        (let [output-dir (file (:kb-data-directory fileset) (:kb-name fileset) (:path-to-rule fileset))
                              rule (:path-to-rule fileset)
                              rulepath (.openStream (clojure.java.io/resource (:path-to-rule fileset)))
                              ]
                          (println "running rule:" (:path-to-rule fileset) "\n"
                                   "output directory:" (.getAbsolutePath output-dir) "\n"
                                   "kb url:" (:kb-url fileset) "\n"
                                   "kb name:" (:kb-name fileset) "\n"
                                   "kb user:" (:kb-user fileset) "\n"
                                   "kb password:" (:kb-password fileset) "\n"
                                   "kb data-directory:" (:kb-data-directory fileset) "\n"
                                   "kb server implementation:" (:kb-server-impl fileset) "\n"
                                   "classpath ")
                          (if-not rule
                            (do (boot.util/fail "There must be a rule specified prior to the run-rule task!") (*usage*)))
                          (.mkdirs output-dir)
                          (commandline-process-forward-rules `{;; The URL to the Rdf4j server to query
                                                               :server-url       ~(:kb-url fileset)
                                                               ;; The name of the repository to connect to
                                                               :repo-name        ~(:kb-name fileset)
                                                               ;; The username to use when connecting
                                                               :username         ~(:kb-user fileset)
                                                               ;; The password to use when connecting
                                                               :password         ~(:kb-password fileset)

                                                               ;; The output directory where generated triple files will be placed.
                                                               ;; IMPORTANT: The output directory path must end with a forward slash.
                                                               :output-directory ~(str (.getAbsolutePath output-dir) "/")

                                                               ;; name of the server implementation, e.g. stardog, blazegraph. Defaults to 'http'
                                                               :server-impl      ~(:kb-server-impl fileset)

                                                               ;; Names of the rule sets to use (must point to files available on the
                                                               ;; classpath; NOTE that it should not start with a forward slash)
                                                               :rule-set-names   #{~(:path-to-rule fileset)}}))
                        fileset))

(defn get-load-file-prefix
  "return the file name prefix to use when invoking a load from the triple store"
  [file-to-load fileset format index]
  ;; file format: ${RULE_NAME}.port_${KB_PORT}.repo_${KB_NAME}.format_${FORMAT}.load
  (str (.getName (file file-to-load)) ".port_" (:kb-port fileset) ".repo_" (:kb-name fileset) ".format_" format "." index))

(defn load-files
  "loads the list of files specified by :files-to-load in the fileset"
  [fileset format]

  (doall (map-indexed (fn [index file-to-load] (let [load-request-dir (:kb-load-request-dir fileset)
                                                     load-file-prefix (get-load-file-prefix file-to-load fileset format index)
                                                     load-file (file load-request-dir (str load-file-prefix ".load"))
                                                     success-file (file load-request-dir (str load-file-prefix ".load.success"))
                                                     error-file (file load-request-dir (str load-file-prefix ".load.error"))
                                                     start-time (System/currentTimeMillis)
                                                     load-time-threshold-in-ms 1800000] ; 30 min

                                                 (println "loading file: " file-to-load)

                                                 ;; if old success or error files exist, delete them
                                                 (if (.exists load-file) (.delete load-file))
                                                 (if (.exists success-file) (.delete success-file))
                                                 (if (.exists error-file) (.delete error-file))

                                                 ;; submit the load request
                                                 (spit load-file file-to-load)

                                                 ;; then wait for the load to complete
                                                 (loop []
                                                   (Thread/sleep 10000) ;; check for the .success or .error files every 10s
                                                   (cond
                                                     (.exists success-file) (println "load success for " file-to-load)
                                                     (.exists error-file) (throw (IllegalStateException.
                                                                                   (str "Load failed for file:"
                                                                                        (trim-newline (slurp load-file)))))
                                                     (< load-time-threshold-in-ms
                                                        (- (System/currentTimeMillis) start-time)) (throw (IllegalStateException.
                                                                                                            (str "Halting load due to timeout (> 30 min) for file:"
                                                                                                                 (trim-newline (slurp load-file)))))
                                                     :else (recur)))))

                      (:files-to-load fileset))))

(deftask load-file-list
         "load a list of files into the KB"
         [l list-file VAL str "file containing list of files to load (one per line)"
          f format VAL str "format of files that will be loaded, e.g. ntriples"]
         (with-pre-wrap fileset
                        (load-files (merge fileset {:files-to-load (map (fn [line] (.trim line)) (line-seq (BufferedReader. (FileReader. list-file))))}) format)
                        ;; purposefully not including the :files-to-load in the fileset returned by this function since it's only needed for the load
                        fileset))

(deftask load-rule []
         "load output from a rule"
         (with-pre-wrap fileset
                        (let [rule-path (:path-to-rule fileset)
                              output-dir (file (:kb-data-directory fileset) (:kb-name fileset) rule-path)
                              rules (kabob.core.rule/kabob-load-rules-from-classpath rule-path)
                              files-to-load (map (fn [rule]
                                                   (.trim (.getAbsolutePath (file output-dir (str (:name rule) ".nt\n")))))
                                                 rules)]
                          ;; make sure all files-to-load exist
                          (doall (map (fn [f] (if (not (.exists (file f)))
                                                (throw (IllegalStateException. (str "Requested file to load does not exist: " f)))))
                                      files-to-load))
                          (load-files (merge fileset {:files-to-load files-to-load}) "ntriples"))
                        ;; purposefully not including the :files-to-load in the fileset returned by this function since it's only needed for the load
                        fileset))



(deftask drop-rule []
         "drops the graph(s) for the active rule directory"
         (with-pre-wrap fileset
                        (let [server-params {:repo-name   (:kb-name fileset)
                                             :username    (:kb-user fileset)
                                             :password    (:kb-password fileset)
                                             :server-url  (:kb-url fileset)
                                             :server-impl (:kb-server-impl fileset)}
                              rule-path (:path-to-rule fileset)
                              rules (kabob.core.rule/kabob-load-rules-from-classpath rule-path)
                              output-dir (file (:kb-data-directory fileset) (:kb-name fileset) rule-path)]
                          (doall (map (fn [rule] (let [output-file (file output-dir (str (:name rule) ".nt"))
                                                       graph-name (str "<file://" (.getAbsolutePath output-file) ">")]
                                                   (if (not (.exists output-file))
                                                     (throw (IllegalStateException. (str "Cannot drop graph: " graph-name " File does not exist: " output-file))))
                                                   (run-update server-params (str "drop graph " graph-name))))
                                      rules)))
                        fileset))


;; Blazegraph-specific tasks
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(deftask virtual-graph-gen []
         "create a virtual graph encompassing all named graphs so that queries can cross named graphs"
         (comp (rule :path-to-rule "rules/util/blazegraph/virtualgraph") (print-rule) (run-rule) (print-rule) (load-rule)))


;; KaBOB implementation tasks
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(deftask compile-ice-nt-file-list []
         "compile list of ICE ntriples files to load"
         (with-pre-wrap fileset
                        (merge fileset {:ice-nt-files (filter (fn [file] (.endsWith (.getName file) ".nt.gz"))
                                                              (file-seq (file (:kb-ice-directory fileset))))})))

(deftask compile-ice-owl-file-list []
         "compile lists of ICE RDF files to load"
         (with-pre-wrap fileset
                        (merge fileset {:ice-owl-files (filter (fn [file] (.endsWith (.getName file) ".owl"))
                                                               (file-seq (file (:kb-ice-directory fileset))))})))

(deftask compile-ontology-file-list []
         "compile lists of ontology files to load"
         (with-pre-wrap fileset
                        (merge fileset {:ontology-nt-files (filter (fn [file] (.endsWith (.getName file) ".owl.flattened.nt.noblank.nt"))
                                                                   (file-seq (file (:kb-ontology-directory fileset))))})))

(deftask load-ice-nt []
         "load the ICE ntriples files into the KB"
         (with-pre-wrap fileset
                        (load-files (merge fileset {:files-to-load (:ice-nt-files fileset)}) "ntriples")
                        ;; purposefully not including the :files-to-load in the fileset returned by this function since it's only needed for the load
                        fileset))

(deftask load-ice-owl []
         "load the ICE OWL files into the KB"
         (with-pre-wrap fileset
                        (load-files (merge fileset {:files-to-load (:ice-owl-files fileset)}) "rdfxml")
                        ;; purposefully not including the :files-to-load in the fileset returned by this function since it's only needed for the load
                        fileset))

(deftask load-ontology-nt []
         "load the processed ontologies into the KB"
         (with-pre-wrap fileset
                        (load-files (merge fileset {:files-to-load (:ontology-nt-files fileset)}) "ntriples")
                        ;; purposefully not including the :files-to-load in the fileset returned by this function since it's only needed for the load
                        fileset))

(deftask print-ontology-rdf-list []
         "prints the list of ontologies that will be loaded"
         (with-pre-wrap fileset
                        (println "----------- ONTOLOGY FILES -----------")
                        (doall (map #(println (.getAbsolutePath %)) (:ontology-nt-files fileset)))
                        (println "--------------------------------------")
                        fileset))

(deftask print-ice-rdf-list []
         "prints the list of ICE RDF files that will be loaded"
         (with-pre-wrap fileset
                        (println "----------- ICE FILES -----------")
                        (doall (map #(println (.getAbsolutePath %)) (concat (:ice-nt-files fileset)
                                                                            (:ice-owl-files fileset))))
                        (println "---------------------------------")
                        fileset))

(deftask load-ice-rdf []
         "load the ICE RDF into the KB"
         (comp (compile-ice-nt-file-list) (compile-ice-owl-file-list) (print-ice-rdf-list) (load-ice-nt) (load-ice-owl)))

(deftask load-ontology-rdf []
         "load the ICE RDF into the KB"
         (comp (compile-ontology-file-list) (print-ontology-rdf-list) (load-ontology-nt)))

(deftask validate-owl-restrictions []
         "run rules to validate ontology structures"
         (comp (rule :path-to-rule "rules/validation/valid_owl/restriction") (run-rule) (load-rule)))

(deftask validate-rdf-lists []
         "run rules to validate rdf:List structures"
         (comp (rule :path-to-rule "rules/validation/valid_owl/list/step_a") (run-rule) (load-rule)
               (rule :path-to-rule "rules/validation/valid_owl/list/step_b_tmp") (run-rule) (load-rule)
               (rule :path-to-rule "rules/validation/valid_owl/list/step_c") (run-rule) (load-rule)
               (rule :path-to-rule "rules/validation/valid_owl/list/step_b_tmp") (drop-rule)))

(deftask validate-rdf-syntax []
         "run rules to validate RDF syntax"
         (comp (validate-owl-restrictions) (validate-rdf-lists)))


(deftask check-for-http-single-slash-bug []
         "checks the rule directory for instances of 'http:/' and 'https:/'. An exception is thrown if one is found indicating a missing namespace from kabob.core.namespaces"
         (with-pre-wrap fileset
                        (println "Executing single-slash bug check...")
                        (let [rdf-directory (file (:kb-data-directory fileset) (:kb-name fileset))
                              rdf-files (filter #(.isFile %) (file-seq rdf-directory))]
                          (doall (map (fn [f] (println (str "processing file: " (.getAbsolutePath f)))
                                        (doall (map (fn [line]
                                                      (if (re-find #"https?:/[^/]" line)
                                                        (throw (IllegalStateException. (str "Observed indication of a missing namespace (missing forward slash in http(s)://) in file: "
                                                                                            (.getAbsolutePath f)
                                                                                            " -- on line: "
                                                                                            line)))))
                                                    (line-seq (BufferedReader. (FileReader. f))))))
                                      rdf-files)))
                        fileset))


(deftask build-step-a []
         "build kabob step a; ontology ICE record gen"
         (comp (rule :path-to-rule "rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_a_ontology_to_ice/step_aa_id_denotes_concept_non_obo_ns") (run-rule) (load-rule)
               (rule :path-to-rule "rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_a_ontology_to_ice/step_ab_ontology_id_denotes_concept_obo_ns/step_a_tmp") (run-rule) (load-rule)
               (rule :path-to-rule "rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_a_ontology_to_ice/step_ab_ontology_id_denotes_concept_obo_ns/step_b")
               (run-rule)
               (check-for-http-single-slash-bug)
               (load-rule)
               (rule :path-to-rule "rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_a_ontology_to_ice/step_ab_ontology_id_denotes_concept_obo_ns/step_a_tmp")
               (drop-rule)
               (rule :path-to-rule "rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_a_ontology_to_ice/step_ac_ontology_identifier_typing/step_aca_ontology_identifier_typing_by_obo_ns") (run-rule) (load-rule)
               (rule :path-to-rule "rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_a_ontology_to_ice/step_ac_ontology_identifier_typing/step_acb_ontology_identifier_typing_by_obo_file") (run-rule) (load-rule)
               (rule :path-to-rule "rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_a_ontology_to_ice/step_ad_ontology_ice_record_gen") (run-rule) (load-rule)))

(deftask build-step-b []
         "build kabob step b; link ontology identifiers with skos:exactMatch"
         (comp (rule :path-to-rule "rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_b_ontology_id_exact_match/equivalent_class") (run-rule) (load-rule)
               (rule :path-to-rule "rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_b_ontology_id_exact_match/shared_label") (run-rule) (load-rule)
               (rule :path-to-rule "rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_b_ontology_id_exact_match/exact_match") (run-rule) (load-rule)))


(deftask build-step-c []
         "build kabob step c; reactome ICE gen"
         (comp (rule :path-to-rule "rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_ca_add_reactome_utility_classes_to_ice/step_caa") (run-rule) (load-rule)
               (rule :path-to-rule "rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_ca_add_reactome_utility_classes_to_ice/step_cab") (run-rule) (load-rule)
               (rule :path-to-rule "rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_ca_add_reactome_utility_classes_to_ice/step_cac") (run-rule) (load-rule)
               (rule :path-to-rule "rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_ca_add_reactome_utility_classes_to_ice/step_cad") (run-rule) (load-rule)
               (rule :path-to-rule "rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_ca_add_reactome_utility_classes_to_ice/step_cae") (run-rule) (load-rule)

               (rule :path-to-rule "rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_cb_add_reactome_main_classes_to_ice") (run-rule) (load-rule)

               (rule :path-to-rule "rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_cc_add_reactome_class_fields_to_ice/step_cca") (run-rule) (load-rule)
               (rule :path-to-rule "rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_cc_add_reactome_class_fields_to_ice/step_ccb") (run-rule) (load-rule)

               (rule :path-to-rule "rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_cd_add_reactome_extra_go_terms_to_ice") (run-rule) (load-rule)))


(deftask build-step-d []
         "build kabob step d; ICE identifier processing"
         (comp
           (rule :path-to-rule "rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_d_ice_id_processing/step_da_identifier_typing") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_d_ice_id_processing/step_db_identifier_exact_match") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_d_ice_id_processing/step_dc_more_identifier_exact_match") (run-rule) (load-rule)))


(deftask run-id-set-gen []
         "build kabob step e; ID set generation"
         (with-pre-wrap fileset
                        (let [server-params {:server-url  (:kb-url fileset)
                                             :repo-name   (:kb-name fileset)
                                             :username    (:kb-user fileset)
                                             :password    (:kb-password fileset)
                                             :server-impl (:kb-server-impl fileset)}]
                          (.mkdirs (file (:id-set-directory fileset)))
                          (generate-all-id-sets (open-kb server-params) (:id-set-directory fileset)))
                        fileset))

(deftask load-id-sets []
         "load the triple files created during ID set generation"
         (with-pre-wrap fileset
                        (let [files-to-load (map (fn [f] (.getAbsolutePath f))
                                                 (filter #(.isFile %) (file-seq (file (:id-set-directory fileset)))))]
                          (load-files (merge fileset {:files-to-load files-to-load}) "ntriples"))
                        fileset))

(deftask drop-id-sets []
         "drop the triple files created during ID set generation; this is a convenience task as it's not used during the build process."
         (with-pre-wrap fileset
                        (let [server-params {:repo-name   (:kb-name fileset)
                                             :username    (:kb-user fileset)
                                             :password    (:kb-password fileset)
                                             :server-url  (:kb-url fileset)
                                             :server-impl (:kb-server-impl fileset)}]
                          (doall (map (fn [file]
                                        (let [graph-name (str "<file://" (.getAbsolutePath file) ">")]
                                          (run-update server-params (str "drop graph " graph-name))))
                                      (filter #(.isFile %) (file-seq (file (:id-set-directory fileset)))))))
                        fileset))

(deftask build-step-e []
         "build kabob step e; ID set generation"
         (comp
           (rule :path-to-rule "rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_e_id_set_gen_prep") (run-rule) (load-rule)
           (run-id-set-gen)
           (drop-rule)
           (load-id-sets)))


(deftask build-step-f []
         "build kabob step f; bioentity generation"
         (comp (rule :path-to-rule "rules/_1_post_identifier_merge/step_f_bioentity_generation/step_fa_identifier_bioentity_links") (run-rule) (load-rule)
               (rule :path-to-rule "rules/_1_post_identifier_merge/step_f_bioentity_generation/step_fb_obsolete_identifier_bioentity_links") (run-rule) (load-rule)))


(deftask build-step-g []
         "build kabob step g; ontology-to-bio rules"
         (comp (rule :path-to-rule "rules/_1_post_identifier_merge/step_g_ontology_to_bio/step_ga_copy_owl_constructs_to_bio") (run-rule) (load-rule)
               (rule :path-to-rule "rules/_1_post_identifier_merge/step_g_ontology_to_bio/step_gb_copy_labels_to_bio/step_gba_copy_rdfs_labels_to_bio") (run-rule) (load-rule)
               (rule :path-to-rule "rules/_1_post_identifier_merge/step_g_ontology_to_bio/step_gb_copy_labels_to_bio/step_gbb_derive_missing_labels_to_bio") (run-rule) (load-rule)
               (rule :path-to-rule "rules/_1_post_identifier_merge/step_g_ontology_to_bio/step_gc_copy_node_links_to_bio/step_gca_links_to_nil") (run-rule) (load-rule)
               (rule :path-to-rule "rules/_1_post_identifier_merge/step_g_ontology_to_bio/step_gc_copy_node_links_to_bio/step_gcb_temp_link_ont_to_bio_concepts") (run-rule) (load-rule)
               (rule :path-to-rule "rules/_1_post_identifier_merge/step_g_ontology_to_bio/step_gc_copy_node_links_to_bio/step_gcc_transfer_ontology_links_to_bio") (run-rule) (load-rule)
               (update-kb :query "delete { graph ?g {?c ?p ?c}}
                                  where {
                                         select ?c (owl:equivalentClass as ?p) ?g {
                                            graph ?g {
                                                       ?c owl:equivalentClass ?c .
                                                     }
                                         }
                                        }")
               (rule :path-to-rule "rules/_1_post_identifier_merge/step_g_ontology_to_bio/step_gc_copy_node_links_to_bio/step_gcb_temp_link_ont_to_bio_concepts") (drop-rule)))


(deftask build-step-ha []
         "build kabob step ha; bioentity typing"
         (comp
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_ha_bioentity_typing/by_gene_type/step_haa") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_ha_bioentity_typing/by_gene_type/step_hab") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_ha_bioentity_typing/by_gene_type/step_haa") (drop-rule)

           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_ha_bioentity_typing/by_identifier") (run-rule) (load-rule)

           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_ha_bioentity_typing/by_parent_class/step_a") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_ha_bioentity_typing/by_parent_class/step_b") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_ha_bioentity_typing/by_parent_class/step_a") (drop-rule)
           ))


(deftask build-step-hb []
         "build kabob step hb; bioentity labeling"
         (comp
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hb_bioentity_labeling") (run-rule) (load-rule)))


(deftask build-step-hc []
         "build kabob step hc; gene-or-gene-product abstraction gen"
         (comp
           ; todo: reorganize step hca so that temp rules are created in their own step(s) - and then subsequently dropped
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hca_central_dogma/step_a") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hca_central_dogma/step_b") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hca_central_dogma/step_c") (run-rule) (load-rule)

           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcb_assign_taxon/ncbi") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcb_assign_taxon/refseq") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcb_assign_taxon/uniprot") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcb_assign_taxon/via_has_gene_template/step_a") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcb_assign_taxon/via_has_gene_template/step_b") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcb_assign_taxon/via_has_gene_template/step_a") (drop-rule)

           ;(taxon-validation)                               ;; todo - implement this

           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcc_generate_missing_ggp_entities/step_a") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcc_generate_missing_ggp_entities/step_b") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcc_generate_missing_ggp_entities/step_c") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcc_generate_missing_ggp_entities/step_d") (run-rule) (load-rule)


           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcd_generate_gene_abstractions") (run-rule) (load-rule)

           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hce_link_to_gp_abstractions") (run-rule) (load-rule)

           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcc_generate_missing_ggp_entities/step_a") (drop-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcc_generate_missing_ggp_entities/step_b") (drop-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcc_generate_missing_ggp_entities/step_c") (drop-rule)

           ))


(deftask build-step-hd-bioplex []
         "build kabob step hd; bioentity linking"
         (comp
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/bioplex/step_a") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/bioplex/step_b") (run-rule) (load-rule)
           (update-kb :query "PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
                              delete { graph ?g {?s rdfs:subClassOf ?o}} where {
                                       select ?s (ccp:temp_bioplex_interaction as ?o) ?g {
                                              graph ?g {
                                                          ?s rdfs:subClassOf ccp:temp_bioplex_interaction .
                                                        }
                                                }
                                         }")))


(deftask build-step-hd-drugbank []
         "build kabob step hd; bioentity linking"
         (comp
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/drugbank/step_a") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/drugbank/step_b") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/drugbank/step_c") (run-rule) (load-rule)

           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/drugbank/step_a") (drop-rule)
           (update-kb :query "PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
                                    delete { graph ?g {?s rdfs:subClassOf ?o}} where {
                                        select ?s (ccp:temp_drugbank_interaction as ?o) ?g {
                                          graph ?g {
                                            ?s rdfs:subClassOf ccp:temp_drugbank_interaction .
                                          }
                                        }
                                      }")

           (update-kb :query "PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
                              delete { graph ?g {?s ?p ?o}} where {
                                        select ?s (ccp:temp_drug_participant as ?p) ?o ?g {
                                          graph ?g {
                                            ?s ccp:temp_drug_participant ?o .
                                          }
                                        }
                                      }")

           (update-kb :query "PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
                               delete { graph ?g {?s ?p ?o}} where {
                                        select ?s (ccp:temp_target_participant as ?p) ?o ?g {
                                          graph ?g {
                                            ?s ccp:temp_target_participant ?o .
                                            }
                                        }
                                        }")))


(deftask build-step-hd-goa []
         "build kabob step hd; bioentity linking"
         (comp
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/goa/step_a") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/goa/step_b") (run-rule) (load-rule)


           (update-kb :query "PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
                              delete { graph ?g {?s rdfs:subClassOf ?o}} where {
                                          select ?s (ccp:temp_biological_process as ?o) ?g {
                                            graph ?g {
                                              ?s rdfs:subClassOf ccp:temp_biological_process .
                                            }
                                          }
                                        }")

           (update-kb :query "PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
                              delete { graph ?g {?s rdfs:subClassOf ?o}} where {
                                        select ?s (ccp:temp_localization_process as ?o) ?g {
                                          graph ?g {
                                            ?s rdfs:subClassOf ccp:temp_localization_process .
                                          }
                                        }
                                      }")

           (update-kb :query "PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
                              delete { graph ?g {?s rdfs:subClassOf ?o}} where {
                                        select ?s (ccp:temp_molecular_function as ?o) ?g {
                                          graph ?g {
                                            ?s rdfs:subClassOf ccp:temp_molecular_function .
                                          }
                                        }
                                      }")

           (update-kb :query "PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
                              delete { graph ?g {?s ?p ?o}} where {
                                        select ?s (ccp:temp_has_participant as ?p) ?o ?g {
                                          graph ?g {
                                            ?s ccp:temp_has_participant ?o .
                                          }
                                        }
                                      }")))


(deftask build-step-hd-hp []
         "build kabob step hd; bioentity linking"
         (comp
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/hp/step_a") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/hp/step_b") (run-rule) (load-rule)

           (update-kb :query "PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
                              delete { graph ?g {?s rdfs:subClassOf ?o}} where {
                                        select ?s (ccp:temp_human_phenotype as ?o) ?g {
                                          graph ?g {
                                            ?s rdfs:subClassOf ccp:temp_human_phenotype .
                                          }
                                        }
                                      }")

           (update-kb :query "PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
                              delete { graph ?g {?s ?p ?o}} where {
                                        select ?s (ccp:temp_causes as ?p) ?o ?g {
                                          graph ?g {
                                            ?s ccp:temp_causes ?o .
                                          }
                                        }
                                      }")))


(deftask build-step-hd-irefweb []
         "build kabob step hd; bioentity linking"
         (comp
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/irefweb/step_a") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/irefweb/step_b") (run-rule) (load-rule)

           (update-kb :query "PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
                              delete { graph ?g {?s rdfs:subClassOf ?o}} where {
                                        select ?s (ccp:temp_irefweb_binary_interaction as ?o) ?g {
                                          graph ?g {
                                            ?s rdfs:subClassOf ccp:temp_irefweb_binary_interaction .
                                          }
                                        }
                                      }")

           (update-kb :query "PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
                              delete { graph ?g {?s rdfs:subClassOf ?o}} where {
                                        select ?s (ccp:temp_irefweb_nary_interaction as ?o) ?g {
                                          graph ?g {
                                            ?s rdfs:subClassOf ccp:temp_irefweb_nary_interaction .
                                          }
                                        }
                                      }")))


(deftask build-step-hd-pharmgkb []
         "build kabob step hd; bioentity linking"
         (comp
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/pharmgkb/step_a") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/pharmgkb/step_b") (run-rule) (load-rule)

           (update-kb :query "PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
                              delete { graph ?g {?s rdfs:subClassOf ?o}} where {
                                        select ?s (ccp:temp_pharmgkb_interaction as ?o) ?g {
                                          graph ?g {
                                            ?s rdfs:subClassOf ccp:temp_pharmgkb_interaction .
                                          }
                                        }
                                      }")))


(deftask build-step-hd-reactome []
         "build kabob step hd; bioentity linking"
         (comp
           ;; todo - move rules from steps cae and caf here as many of them use denotes links
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/reactome/step_a_add_continuants_to_bio/step_a") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/reactome/step_a_add_continuants_to_bio/step_b") (run-rule) (load-rule)
           ; todo - remove temporary links here
           ))


(deftask build-step-hd []
         "build kabob step hd; bioentity linking"
         (comp
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/_temp_triples") (run-rule) (load-rule)
           (build-step-hd-bioplex)
           (build-step-hd-drugbank)
           (build-step-hd-goa)
           (build-step-hd-hp)
           (build-step-hd-irefweb)
           (build-step-hd-pharmgkb)
           ;(build-step-hd-reactome) ;; reactome rules are still under development

           (rule :path-to-rule "rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/_temp_triples") (drop-rule)
           ))


(deftask build-step-h []
         "build kabob step h; ICE-to-bio rules"
         (comp (build-step-ha) (build-step-hb) (build-step-hc) (build-step-hd)))


(deftask build-step-i []
         "build kabob step i; bio expansion rules (bio-to-bio)"
         (comp
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_i_bio_expansion/class_based_kr/step_a") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_i_bio_expansion/class_based_kr/step_b") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_i_bio_expansion/class_based_kr/step_c") (run-rule) (load-rule)
           (rule :path-to-rule "rules/_1_post_identifier_merge/step_i_bio_expansion/class_based_kr/step_a") (drop-rule)

           (update-kb :query "PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
                              delete { graph ?g {?s rdfs:subClassOf ?o}} where {
                                        select ?s (ccp:temp_location as ?o) ?g {
                                          graph ?g {
                                            ?s rdfs:subClassOf ccp:temp_location .
                                          }
                                        }
                                      }")))
;
;;(rule :path-to-rule "") (run-rule) (load-rule)
;
(deftask build-kabob-blazegraph []
         "build kabob from scratch"
         (comp (server :docker true :blazegraph true :kb-name "kabobhuman")
               (init-kb)
               (load-ontology-rdf)
               (validate-rdf-syntax)
               (backup-kb :backup-label "onts")
               (build-step-a)
               (build-step-b)
               (backup-kb :backup-label "post-b")
               (load-ice-rdf)
               (backup-kb :backup-label "post-ice")
               (build-step-c)
               (build-step-d)
               (build-step-e)
               (backup-kb :backup-label "post-e")
               (build-step-f)
               (build-step-g)
               (backup-kb :backup-label "post-g")
               ;(validate-rdf-syntax)                        ;; todo - give validate rdf syntax an optional graph to limit the search space?
               (build-step-h)
               (build-step-i)
               (backup-kb :backup-label "post-i")))




;(deftask serialize-kb
;         "specify the triple store implementation that will be used and the name of the repository"
;         [a allegrograph bool "Use an Allegrograph back end"
;          b blazegraph bool "Use a Blazegraph back end"
;          s stardog bool "Use a Stardog back end"
;          n kb-name VAL str "KB name"]
;         (with-pre-wrap fileset
;                        (let [journal
;                              include-inferred false])
;                        (ExportKB. connection output-directory RDFFormat/NQUADS include-inferred)
;                        fileset))