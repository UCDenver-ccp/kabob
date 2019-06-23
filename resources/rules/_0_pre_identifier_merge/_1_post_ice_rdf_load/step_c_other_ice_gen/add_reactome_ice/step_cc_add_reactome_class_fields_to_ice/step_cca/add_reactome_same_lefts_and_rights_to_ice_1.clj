`{:description "This rule finds any biochemical reaction described in Reactome with the same left and right participants at the same stoichiometric coefficient. We later define these as participants, not inputs or outputs",
 :name "step_cca-add_reactome_same_lefts_and_rights_to_ice_1",
 :reify ([?/this_participant_record {:ln (:sha-1 ?/participant_record ?/bcr_record), :ns "http://ccp.ucdenver.edu/kabob/ice/" :prefix "R_"}]),
 :head ((?/bcr_record obo/BFO_0000051 ?/this_participant_record)
        (?/this_participant_record rdfs/subClassOf ?/participant_record)
        (?/this_participant_record rdf/type ccp/IAO_EXT_0001941) ;; participant field
        ),
 :body "#add_reactome_same_lefts_and_rights_to_ice_1.clj
PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX bp: <http://www.biopax.org/release/biopax-level3.owl#>
PREFIX kice: <http://ccp.ucdenver.edu/kabob/ice/>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?bcr_record ?participant_record ?right_record { 
 ?bcr bp:left ?participant .
 ?bcr bp:right ?right .
 ?bcr bp:participantStoichiometry ?stoi_1 .
 ?bcr bp:participantStoichiometry ?stoi_2 .
 filter (?stoi_1 != ?stoi_2) . 
 ?stoi_1 bp:physicalEntity ?participant .
 ?stoi_2 bp:physicalEntity ?right .
 ?stoi_1 bp:stoichiometricCoefficient ?coeff_1 . 
 ?stoi_2 bp:stoichiometricCoefficient ?coeff_2 . 
 filter (?coeff_1 = ?coeff_2) .
 filter (?participant = ?right) .
 ?bcr ccp:ekws_temp_biopax_connector_relation ?bcr_record .
 ?participant ccp:ekws_temp_biopax_connector_relation ?participant_record .
 ?right ccp:ekws_temp_biopax_connector_relation ?right_record .
}",
  :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}




         

         
