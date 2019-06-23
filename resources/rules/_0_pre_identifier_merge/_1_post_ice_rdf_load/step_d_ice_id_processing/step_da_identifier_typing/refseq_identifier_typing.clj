;; ----------------------------------------------------
;; --------- refseq identifier typing ---------
;; ----------------------------------------------------
`{:name "step-da_refseq-identifier-typing"
  :description "This rule types all 'refseq identifiers' as explicit subclasses of that concept (IAO_EXT_0000263). Doing so helps avoid some *'s in downstream queries. Currently, this rule returns zero hits but we will leave it in case it is needed in the future, thus allowing us to safely keep some *'s out of later queries."
  :head ((?/identifier rdfs/subClassOf ccp/IAO_EXT_0000263)) ; CCP:refseq_identifier
  :reify ()
  :body "prefix franzOption_chunkProcessingAllowed: <franz:yes>
  prefix franzOption_clauseReorderer: <franz:identity>
                  prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                  prefix obo: <http://purl.obolibrary.org/obo/>
                  prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                  prefix ccp: <http://ccp.ucdenver.edu/obo/ext/>
                  select distinct ?identifier {
                     ?identifier rdfs:subClassOf* ccp:IAO_EXT_0000263 .
                     filter (contains (str(?identifier), 'http://ccp.ucdenver.edu/kabob/ice/'))
                     # exclude those identifiers that already have a direct connection to ccp:IAO_EXT_0000263
                     filter not exists {?identifier rdfs:subClassOf ccp:IAO_EXT_0000263}
                  }"
  }