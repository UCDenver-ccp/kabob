`{:description "This rule finds any controlled field described in Reactome.",
 :name "step_cca-add_reactome_controlleds_to_ice",
  :reify ([?/this_controlled_record {:ln (:sha-1 ?/controlled_record ?/catalysis_or_control_record), :ns "http://ccp.ucdenver.edu/kabob/ice/" :prefix "R_"}]),
  :head ((?/catalysis_or_control_record obo/BFO_0000051 ?/this_controlled_record)
         (?/this_controlled_record rdfs/subClassOf ?/controlled_record)
        (?/this_controlled_record rdf/type ccp/IAO_EXT_0001567) ;; controlled field
        ),
 :body "#add_reactome_controlleds_to_ice.clj
PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX bp: <http://www.biopax.org/release/biopax-level3.owl#>
PREFIX kice: <http://ccp.ucdenver.edu/kabob/ice/>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?catalysis_or_control_record ?controlled_record WHERE {
 ?catalysis_or_control bp:controlled ?controlled .
 ?catalysis_or_control ccp:ekws_temp_biopax_connector_relation ?catalysis_or_control_record .
 ?controlled ccp:ekws_temp_biopax_connector_relation ?controlled_record .
}",
  :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}




         

         
