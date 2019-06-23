`{:description "This rule finds any product field described in Reactome; these are outputs of template reactions.",
 :name "step_cca-add_reactome_products_to_ice",
  :reify ([?/this_product_record {:ln (:sha-1 ?/product_record ?/tmplr_record), :ns "http://ccp.ucdenver.edu/kabob/ice/" :prefix "R_"}]),
 :head ((?/tmplr_record obo/BFO_0000051 ?/this_product_record)
         (?/this_product_record rdfs/subClassOf ?/product_record)
        (?/this_product_record rdf/type ccp/IAO_EXT_0001550) ;; right field
        ),
 :body "#add_reactome_products_to_ice.clj
PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX bp: <http://www.biopax.org/release/biopax-level3.owl#>
PREFIX kice: <http://ccp.ucdenver.edu/kabob/ice/>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?tmplr_record ?product_record WHERE {
 ?tmplr bp:product ?product .
 ?tmplr ccp:ekws_temp_biopax_connector_relation ?tmplr_record .
 ?product ccp:ekws_temp_biopax_connector_relation ?product_record .
}",
  :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}




         

         
