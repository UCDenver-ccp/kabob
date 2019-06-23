;; ----------------------------------------------------
;; --------- ontology identifier typing ---------
;; ----------------------------------------------------
`{:name "step-e-ontology-identifier-typing"
  :description "This rule types all 'ontology identifier' as explicit subclasses of that concept (IAO_EXT_0000307). Doing so helps avoid some *'s in downstream queries."
  :head ((?/identifier rdfs/subClassOf ccp/IAO_EXT_0000307)) ; CCP:ontology_identifier
  :reify ()
  :body "prefix franzOption_chunkProcessingAllowed: <franz:yes>
  prefix franzOption_clauseReorderer: <franz:identity>
                  prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                  prefix obo: <http://purl.obolibrary.org/obo/>
                  prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                  prefix ccp: <http://ccp.ucdenver.edu/obo/ext/>
                  select distinct ?identifier {
                     ?identifier rdfs:subClassOf* ccp:IAO_EXT_0000307 .
                     # exclude those identifiers that already have a direct connection to ccp:IAO_EXT_0000307
                     filter not exists {?identifier rdfs:subClassOf ccp:IAO_EXT_0000307}
                  }"
  }