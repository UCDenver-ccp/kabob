`{:description "This rule finds any feature field (modification or fragment) described in Reactome.",
 :name "step_cca-add_reactome_features_to_ice",
  :reify ([?/this_feature_record {:ln (:sha-1 ?/feature_record ?/continuant_record), :ns "http://ccp.ucdenver.edu/kabob/ice/" :prefix "R_"}]),
  :head ((?/continuant_record obo/BFO_0000051 ?/this_feature_record)
         (?/this_feature_record rdfs/subClassOf ?/feature_record)
        (?/this_feature_record rdf/type ccp/IAO_EXT_0001527) ;; feature field
        ),
 :body "#add_reactome_features_to_ice.clj
PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX bp: <http://www.biopax.org/release/biopax-level3.owl#>
PREFIX kice: <http://ccp.ucdenver.edu/kabob/ice/>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?continuant_record ?feature_record WHERE {
 ?continuant bp:feature ?feature .
 ?continuant ccp:ekws_temp_biopax_connector_relation ?continuant_record .
 ?feature ccp:ekws_temp_biopax_connector_relation ?feature_record .
}",
  :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}




         

         
