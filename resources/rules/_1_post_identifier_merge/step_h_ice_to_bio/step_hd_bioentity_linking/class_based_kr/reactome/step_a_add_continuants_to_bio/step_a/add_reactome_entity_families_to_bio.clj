`{:description "This rule finds any functional family described in Reactome, generates its ICE identifier, and places the top-level entity list on the BIO side.  It lays the groundwork for building these into RDF lists, but they are connected programmatically in part 2.",
 :name "step-hda-reactome_add-reactome-entity-families-to-bio",
 :reify ([?/union_class {:ns "kbio" :prefix "B_", :ln (:sha-1 "Reactome union set" ?/family_record)}]
         [?/extra_node {:ns "kbio" :prefix "B_", :ln (:sha-1 "Reactome set extra node" ?/family_record)}],
         [?/member_list {:ln (:sha-1 "rdf list" ?/family_record ?/member_bio), :ns "kbio" :prefix "L_"}]),
 :head ((?/family_reactome_id obo/IAO_0000219 ?/union_class)
        ;; each reactome ID connects to a set of list nodes
        (?/family_reactome_id ccp/ekws_temp_list_connector_relation ?/member_list)
        (?/union_class owl/equivalentClass ?/extra_node)
        (?/member_list rdf/first ?/member_bio)
        (?/member_list rdf/type rdf/List)),
 :body "PREFIX franzOption_memoryLimit: <franz:85g>
PREFIX franzOption_memoryExhaustionWarningPercentage: <franz:95>
PREFIX franzOption_logQuery: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?family_reactome_id ?member_bio ?family_record ?member_record ?name
WHERE {
?family_record obo:BFO_0000051 ?member_record .
?member_record rdf:type ccp:IAO_EXT_0001528 . # member physical entity field
?family_record obo:BFO_0000051 ?family_xref_record .
?family_xref_record rdf:type ccp:IAO_EXT_0001588 . # xref field
?family_xref_record obo:BFO_0000051 ?family_xref_id_field .
?family_xref_id_field rdf:type ?family_reactome_id .
?family_reactome_id rdfs:subClassOf ccp:IAO_EXT_0001643 . # Reactome identifier
?member_record obo:BFO_0000051 ?member_xref_record .
?member_xref_record rdf:type ccp:IAO_EXT_0001588 . # xref field
?member_xref_record obo:BFO_0000051 ?member_xref_id_field .
?member_xref_id_field rdf:type ?member_reactome_id .
?member_reactome_id rdfs:subClassOf ccp:IAO_EXT_0001643 . # Reactome identifier
?member_reactome_id obo:IAO_0000219 ?member_bio .
}",
 :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}

        
