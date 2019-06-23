`{:description "This rule finds any pathway order field described in Reactome; these define causal relations between pathway steps.",
 :name "step_cca-add_reactome_pathway_orders_to_ice",
 :reify ([?/this_pathway_order_record {:ln (:sha-1 ?/pathway_order_record ?/pathway_record), :ns "http://ccp.ucdenver.edu/kabob/ice/" :prefix "R_"}]),
  :head ((?/pathway_record obo/BFO_0000051 ?/this_pathway_order_record)
        (?/this_pathway_order_record rdfs/subClassOf ?/pathway_order_record)
        (?/this_pathway_order_record rdf/type ccp/IAO_EXT_0001542) ;; pathway order field
        ),
 :body "#add_reactome_pathway_orders_to_ice.clj
PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX bp: <http://www.biopax.org/release/biopax-level3.owl#>
PREFIX kice: <http://ccp.ucdenver.edu/kabob/ice/>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?pathway_record ?pathway_order_record WHERE {
 ?pathway bp:pathwayOrder ?pathway_order .
 ?pathway ccp:ekws_temp_biopax_connector_relation ?pathway_record .
 ?pathway_order ccp:ekws_temp_biopax_connector_relation ?pathway_order_record .
}",
  :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}




         

         
