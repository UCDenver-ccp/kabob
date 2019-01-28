`{:description "This rule finds any sequence interval record described in Reactome; it requires the sequence sites that define its start and end to be in the KB first.",
 :name "add_reactome_sequence_intervals_to_ice",
  :reify ([?/interval_record {:ns "http://ccp.ucdenver.edu/kabob/ice/", :ln (:sha-1 "Reactome sequence interval record" ?/interval), :prefix "R_"}]
          [?/this_start_site_record {:ns "http://ccp.ucdenver.edu/kabob/ice/", :ln (:sha-1 ?/interval_record ?/start_site_record), :prefix "R_"}]
          [?/this_end_site_record {:ns "http://ccp.ucdenver.edu/kabob/ice/", :ln (:sha-1 ?/interval_record ?/ens_site_record), :prefix "R_"}]
          ),
 :head ((?/interval_record rdf/type ccp/IAO_EXT_0001576) ;; sequence interval
        (?/interval_record obo/BFO_0000051 ?/this_start_site_record)
        (?/this_start_site_record rdfs/subClassOf ?/start_site_record)
        (?/this_start_site_record rdf/type ccp/IAO_EXT_0001534) ;; sequence interval start field
        (?/interval_record obo/BFO_0000051 ?/this_end_site_record)
        (?/this_end_site_record rdfs/subClassOf ?/end_site_record)
        (?/this_end_site_record rdf/type ccp/IAO_EXT_0001535) ;; sequence interval end field
        (?/interval ccp/ekws_temp_biopax_connector_relation ?/interval_record)),
 :body "#add_reactome_sequence_intervals_to_ice.clj
PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX bp: <http://www.biopax.org/release/biopax-level3.owl#>
PREFIX kice: <http://ccp.ucdenver.edu/kabob/ice/>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?interval ?start_site_record ?end_site_record WHERE {
?interval rdf:type bp:SequenceInterval .
?interval bp:sequenceIntervalBegin ?start_site .
?interval bp:sequenceIntervalEnd ?end_site .
?start_site ccp:ekws_temp_biopax_connector_relation ?start_site_record .
?end_site ccp:ekws_temp_biopax_connector_relation ?end_site_record .
}",
   :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}


 

