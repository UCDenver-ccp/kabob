`{:description "This rule finds any complex record described in Reactome.  The rule generates the Reactome complex ICE identifier, and places the top-level entity on the BIO side as a localized entity.",
 :name "step-hda-reactome_add-reactome-complexes-to-bio",
 :reify ([?/complex_entity {:ln (:sha-1 "bio-side reactome complex" ?/complex_record), :ns "kbio" :prefix "B_"}]),
 :head ((?/reactome_id obo/IAO_0000219 ?/complex_entity)),
 :body "PREFIX franzOption_memoryLimit: <franz:85g>
PREFIX franzOption_memoryExhaustionWarningPercentage: <franz:95>
PREFIX franzOption_logQuery: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?reactome_id ?complex_record
WHERE {
       ?complex_record rdf:type ccp:IAO_EXT_0001516 .  # complex record
       ?complex_record obo:BFO_0000051 ?react_xref_record .
       ?react_xref_record rdf:type ccp:IAO_EXT_0001588 . # xref field
       ?react_xref_record obo:BFO_0000051 ?react_id_field .
       ?react_id_field rdf:type ?reactome_id .
       ?reactome_id rdfs:subClassOf ccp:IAO_EXT_0001643 .
}",
 :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}
