{:description "This rule finds any previously reified protein record from Reactome that belongs to a functional group of proteins/physical entities, and traces out the relation between the group entity and the member entities.",
 :name "add_protein_record_member_entity_references_from_human_reactome_to_ice_step_g",
 :head ((?/prot_record obo/BFO_0000051 ?/member_prot_record)
        (?/member_prot_record rdf/type ccp/IAO_EXT_0001528)),
 :sparql-string "#add_protein_record_member_entity_references_from_human_reactome_to_ice_step_g.clj
PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX bp: <http://www.biopax.org/release/biopax-level3.owl#>
PREFIX bp3: <http://www.reactome.org/biopax/63/48887#>
PREFIX kice: <http://ccp.ucdenver.edu/kabob/ice/>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT ?prot_record ?member_prot_record 
WHERE {
?member_prot rdf:type bp:Protein .
GRAPH <file://add_protein_records_and_reactome_ids_from_human_reactome_to_ice_step_a.nt> {
?member_prot obo:IAO_0000142 ?member_prot_record . 
} 
# set bp:memberPhysicalEntity set_member
?prot bp:memberPhysicalEntity ?member_prot .
?prot obo:IAO_0000142 ?prot_record .
}",
 :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}