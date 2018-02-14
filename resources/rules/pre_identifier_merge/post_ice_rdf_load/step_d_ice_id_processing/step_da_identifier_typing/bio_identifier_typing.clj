;; ----------------------------------------------------
;; --------- refseq genomic identifier typing ---------
;; ----------------------------------------------------
`{:name "bio-identifier-typing"
  :description "This rule types all 'identifiers of a biological entity' as explicit subclasses of that concept (IAO_EXT_0000342). Doing so helps avoid some *'s in downstream queries."
  :head ((?/identifier rdfs/subClassOf ccp/IAO_EXT_0000342)) ; CCP:identifier_of_a_biological_entity
  :reify ()
  :sparql-string "prefix franzOption_chunkProcessingAllowed: <franz:yes>
                  prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                  prefix obo: <http://purl.obolibrary.org/obo/>
                  prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                  prefix ccp: <http://ccp.ucdenver.edu/obo/ext/>
                  select ?identifier {
                     ?identifier rdfs:subClassOf* ccp:IAO_EXT_0000342 .
                  }"
  }