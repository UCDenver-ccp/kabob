#!/bin/bash -e
#

# This script requires two arguments:
# 1) triple-store-specific script directory
# 2) kb name
# 3) server implementation, e.g. stardog, blazegraph, default (http), etc.

BASE_SCRIPT_DIR=$1
COMMON_SCRIPT_DIR=/kabob.git/scripts/docker/common-scripts
chmod -R 755 ${BASE_SCRIPT_DIR}
chmod -R 755 ${COMMON_SCRIPT_DIR}

# This script takes a single argument specifying the repository name to use/construct
export KB_INSTANCE_NAME=$2
export DOCKER_ENV=${BASE_SCRIPT_DIR}/docker-env.sh

SERVER_IMPL=$3

source ${DOCKER_ENV}
source ${COMMON_SCRIPT_DIR}/ENV.sh

echo "LEININGEN=${LEININGEN}"
echo "KB_PORT=${KB_PORT}"
echo "KB_URL=${KB_URL}"
echo "KB_USER=${KB_USER}"
echo "KB_PASS=${KB_PASS}"
echo "KB_NAME=${KB_NAME}"
echo "DATASOURCE_OWL_DIR=${DATASOURCE_OWL_DIR}"
echo "DATASOURCE_ICE_DIR=${DATASOURCE_ICE_DIR}"
echo "KB_DATA_DIR=${KB_DATA_DIR}"
echo "BACKEND IMPLEMENTATION=${SERVER_IMPL}"

############ Clean out the directory in which we're going to place our artifacts.
######rm -rvf ${KB_DATA_DIR}
######mkdir -p ${KB_DATA_DIR}
#########
########### create a new database
#${BASE_SCRIPT_DIR}/create-new-database.sh ${KB_NAME}
########
########### generate lists of RDF files that will be loaded in subsequent steps
#${COMMON_SCRIPT_DIR}/generate-rdf-file-lists.sh ${KB_NAME} ${DOCKER_ENV}
########
########## Load the ontologies (note: they will have been converted from OWL to n-triples prior to loading)
#${BASE_SCRIPT_DIR}/load-list-file.sh \
#  ${KB_PORT} \
#  ${KB_NAME} \
#  ${KB_DATA_DIR}/file-lists/owl-files.${KB_NAME}.list \
#  "ntriples"


${BASE_SCRIPT_DIR}/RULES.sh rules/util/blazegraph/virtualgraph

######### Check that there are no classes with redundant restrictions after the ontology load
#${BASE_SCRIPT_DIR}/RULES.sh rules/validation/valid_owl/restriction
#${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/valid_owl/restriction
#${BASE_SCRIPT_DIR}/RULES.sh rules/validation/valid_owl/list
#${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/valid_owl/list
##
############
##############
################# ============================================================================================ #
################# =======================   PRE IDENTIFIER MERGE RULES ARE RUN BELOW   ======================= #
################# ============================================================================================ #
##############
############### create ICE records for all ontology concepts
#${BASE_SCRIPT_DIR}/RULES.sh rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_a_ontology_to_ice/step_aa_id_denotes_concept_non_obo_ns
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_a_ontology_to_ice/step_aa_id_denotes_concept_non_obo_ns
#${BASE_SCRIPT_DIR}/RULES.sh rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_a_ontology_to_ice/step_ab_ontology_id_denotes_concept_obo_ns
#
### check for http:/ bug before proceeding:
### grep -Rle 'http:/[^/]' rules/
### grep -Rle 'https:/[^/]' rules/
#
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_a_ontology_to_ice/step_ab_ontology_id_denotes_concept_obo_ns
#${BASE_SCRIPT_DIR}/RULES.sh rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_a_ontology_to_ice/step_ac_ontology_identifier_typing/step_aca_ontology_identifier_typing_by_obo_ns
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_a_ontology_to_ice/step_ac_ontology_identifier_typing/step_aca_ontology_identifier_typing_by_obo_ns
#${BASE_SCRIPT_DIR}/RULES.sh rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_a_ontology_to_ice/step_ac_ontology_identifier_typing/step_acb_ontology_identifier_typing_by_obo_file
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_a_ontology_to_ice/step_ac_ontology_identifier_typing/step_acb_ontology_identifier_typing_by_obo_file
#${BASE_SCRIPT_DIR}/RULES.sh rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_a_ontology_to_ice/step_ad_ontology_ice_record_gen
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_a_ontology_to_ice/step_ad_ontology_ice_record_gen
#########
############### create skos:exactMatch links between equivalent ontology identifiers
############ DEPRECATED ${BASE_SCRIPT_DIR}/RULES.sh rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_b_ontology_id_exact_match/identifier_normalization
############ DEPRECATED ${BASE_SCRIPT_DIR}/LOAD.sh rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_b_ontology_id_exact_match/identifier_normalization
############ DEPRECATED ${BASE_SCRIPT_DIR}/RULES.sh rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_b_ontology_id_exact_match/ontology_xref
############ DEPRECATED${BASE_SCRIPT_DIR}/LOAD.sh rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_b_ontology_id_exact_match/ontology_xref
#${BASE_SCRIPT_DIR}/RULES.sh rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_b_ontology_id_exact_match/equivalent_class
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_b_ontology_id_exact_match/equivalent_class
#${BASE_SCRIPT_DIR}/RULES.sh rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_b_ontology_id_exact_match/shared_label
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_0_pre_identifier_merge/_0_pre_ice_rdf_load/step_b_ontology_id_exact_match/shared_label
#########
########### Load the ICE RDF - the rules above process the ontologies only, so we have waited to load the ICE RDF until this point
#${BASE_SCRIPT_DIR}/load-list-file.sh \
#  ${KB_PORT} \
#  ${KB_NAME} \
#  ${KB_DATA_DIR}/file-lists/ice-nt-files.${KB_NAME}.list
#
#${BASE_SCRIPT_DIR}/load-list-file.sh \
#  ${KB_PORT} \
#  ${KB_NAME} \
#  ${KB_DATA_DIR}/file-lists/ice-owl-files.${KB_NAME}.list \
#  "rdfxml"

######### OPTIMIZE STORE
#${BASE_SCRIPT_DIR}/OPTIMIZE.sh
#sleep 300
##
##
#
#######
#######
############# generate other ICE RDF

## step_ca
#${BASE_SCRIPT_DIR}/RULES.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_ca_add_reactome_utility_classes_to_ice/step_caa
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_ca_add_reactome_utility_classes_to_ice/step_caa
#${BASE_SCRIPT_DIR}/RULES.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_ca_add_reactome_utility_classes_to_ice/step_cab
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_ca_add_reactome_utility_classes_to_ice/step_cab
#${BASE_SCRIPT_DIR}/RULES.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_ca_add_reactome_utility_classes_to_ice/step_cac
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_ca_add_reactome_utility_classes_to_ice/step_cac
#${BASE_SCRIPT_DIR}/RULES.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_ca_add_reactome_utility_classes_to_ice/step_cad
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_ca_add_reactome_utility_classes_to_ice/step_cad
#${BASE_SCRIPT_DIR}/RULES.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_ca_add_reactome_utility_classes_to_ice/step_cae
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_ca_add_reactome_utility_classes_to_ice/step_cae
#
## step_cb
#${BASE_SCRIPT_DIR}/RULES.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_cb_add_reactome_main_classes_to_ice
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_cb_add_reactome_main_classes_to_ice
#
## step_cc
#${BASE_SCRIPT_DIR}/RULES.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_cc_add_reactome_class_fields_to_ice/step_cca
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_cc_add_reactome_class_fields_to_ice/step_cca
#${BASE_SCRIPT_DIR}/RULES.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_cc_add_reactome_class_fields_to_ice/step_ccb
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_cc_add_reactome_class_fields_to_ice/step_ccb
#
## step_cd
#${BASE_SCRIPT_DIR}/RULES.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_cd_add_reactome_extra_go_terms_to_ice
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_c_other_ice_gen/add_reactome_ice/step_cd_add_reactome_extra_go_terms_to_ice
#
#
######
############# process ICE identifiers
#${BASE_SCRIPT_DIR}/RULES.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_d_ice_id_processing/step_da_identifier_typing
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_d_ice_id_processing/step_da_identifier_typing
#${BASE_SCRIPT_DIR}/RULES.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_d_ice_id_processing/step_db_identifier_exact_match
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_d_ice_id_processing/step_db_identifier_exact_match
####
###### todo: restart required here? seems to stall otherwise; maybe try optimize?
####
####
#${BASE_SCRIPT_DIR}/RULES.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_d_ice_id_processing/step_dc_more_identifier_exact_match
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_0_pre_identifier_merge/_1_post_ice_rdf_load/step_d_ice_id_processing/step_dc_more_identifier_exact_match
#
#########
###########
###########
############ ============================================================================================ #
############ ============================   END PRE IDENTIFIER MERGE RULES   ============================ #
############ ============================================================================================ #
###########
###########
########### Create the ID sets (step e)
##export LEIN_ROOT=true
#cd /kabob.git && { ${LEININGEN} generate-id-sets ${KB_URL} ${KB_NAME} ${KB_USER} ${KB_PASS} ${KB_DATA_DIR}/id_sets/exact/ ${KB_DATA_DIR}/id_sets/graph_dbs/ ${SERVER_IMPL} ; cd - ; }
#${BASE_SCRIPT_DIR}/LOAD.sh id_sets/exact

####
########### OPTIMIZE STORE
####${BASE_SCRIPT_DIR}/OPTIMIZE.sh
####sleep 180
####
#######
########
######### ============================================================================================ #
######### =======================   POST IDENTIFIER MERGE RULES ARE RUN BELOW   ====================== #
######### ============================================================================================ #
########
########
##########  create bioentity for each id set
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_f_bioentity_generation/step_fa_identifier_bioentity_links
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_f_bioentity_generation/step_fa_identifier_bioentity_links
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_f_bioentity_generation/step_fb_obsolete_identifier_bioentity_links
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_f_bioentity_generation/step_fb_obsolete_identifier_bioentity_links
#######
####
####
######### Check for identifiers that denote multiple kabob bioentities
#${BASE_SCRIPT_DIR}/RULES.sh rules/validation/ice_bio_distinction
#${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/ice_bio_distinction
##
######
################ connect bioentities based on ontology hierarchies
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_g_ontology_to_bio/step_ga_copy_owl_constructs_to_bio
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_g_ontology_to_bio/step_ga_copy_owl_constructs_to_bio
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_g_ontology_to_bio/step_gb_copy_labels_to_bio/step_gba_copy_rdfs_labels_to_bio
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_g_ontology_to_bio/step_gb_copy_labels_to_bio/step_gba_copy_rdfs_labels_to_bio
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_g_ontology_to_bio/step_gb_copy_labels_to_bio/step_gbb_derive_missing_labels_to_bio
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_g_ontology_to_bio/step_gb_copy_labels_to_bio/step_gbb_derive_missing_labels_to_bio
###
############ OPTIMIZE STORE
#####${BASE_SCRIPT_DIR}/OPTIMIZE.sh
#####sleep 300
####
####
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_g_ontology_to_bio/step_gc_copy_node_links_to_bio/step_gca_links_to_nil
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_g_ontology_to_bio/step_gc_copy_node_links_to_bio/step_gca_links_to_nil
#
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_g_ontology_to_bio/step_gc_copy_node_links_to_bio/step_gcb_temp_link_ont_to_bio_concepts
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_g_ontology_to_bio/step_gc_copy_node_links_to_bio/step_gcb_temp_link_ont_to_bio_concepts
#
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_g_ontology_to_bio/step_gc_copy_node_links_to_bio/step_gcc_transfer_ontology_links_to_bio
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_g_ontology_to_bio/step_gc_copy_node_links_to_bio/step_gcc_transfer_ontology_links_to_bio
###
#
## todo: need to have stardog use the admin user to allow kb updates/deletes that are called for in step gcd -- for now do them manually (see queries below)
#
######${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_g_ontology_to_bio/step_gc_copy_node_links_to_bio/step_gcd_link_removal
###### is there anyting to load??? ${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_g_ontology_to_bio/step_gc_copy_node_links_to_bio/step_gcd_link_removal
#
##delete { graph ?g {?c ?p ?c}} where {
##  select ?c (owl:equivalentClass as ?p) ?g {
##    graph ?g {
##      ?c owl:equivalentClass ?c .
##    }
##  }
##}
#
##delete { graph ?g {?s ?p ?o}} where {
##                    select ?s (ccp:temporary_link as ?p) ?o ?g {
##                      graph ?g {
##                        ?s ccp:temporary_link ?o .
##                      }
##                    }
##                  }
#
######## Validate RDF syntax
#${BASE_SCRIPT_DIR}/RULES.sh rules/validation/valid_owl/restriction
#${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/valid_owl/restriction
#${BASE_SCRIPT_DIR}/RULES.sh rules/validation/valid_owl/list
#${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/valid_owl/list
##
############# ice to bio
########## typing
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_ha_bioentity_typing/by_gene_type/step_haa
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_ha_bioentity_typing/by_gene_type/step_haa
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_ha_bioentity_typing/by_gene_type/step_hab
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_ha_bioentity_typing/by_gene_type/step_hab
####${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_ha_bioentity_typing/by_gene_type/step_hac
#### anything to load?? ${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_ha_bioentity_typing/by_gene_type/step_hac
#
## todo: need to have stardog use the admin user to allow kb updates/deletes that are called for in step hac -- for now do them manually (see queries below)
#

##delete { graph ?g {?s rdfs:subClassOf ?o}} where {
##                    select ?s (kice:temp_bio_region as ?o) ?g {
##                      graph ?g {
##                        ?s rdfs:subClassOf kice:temp_bio_region .
##                      }
##                    }
##                  }
##
##delete { graph ?g {?s rdfs:subClassOf ?o}} where {
##                    select ?s (kice:temp_protein_coding_gene as ?o) ?g {
##                      graph ?g {
##                        ?s rdfs:subClassOf kice:temp_protein_coding_gene .
##                      }
##                    }
##                  }
##
##delete { graph ?g {?s rdfs:subClassOf ?o}} where {
##                    select ?s (kice:temp_pseudogene as ?o) ?g {
##                      graph ?g {
##                        ?s rdfs:subClassOf kice:temp_pseudogene .
##                      }
##                    }
##                  }

#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_ha_bioentity_typing/by_identifier
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_ha_bioentity_typing/by_identifier
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_ha_bioentity_typing/by_parent_class
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_ha_bioentity_typing/by_parent_class
###
#######
#######
######### labeling
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hb_bioentity_labeling
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hb_bioentity_labeling
######
#####
######## ggp abstractions
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hca_central_dogma/step_a
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hca_central_dogma/step_a
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hca_central_dogma/step_b
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hca_central_dogma/step_b
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hca_central_dogma/step_c
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hca_central_dogma/step_c
#
#
##delete { graph ?g {?s ?p ?o}} where {
###                    select ?s (ccp:temp_possible_hgt_restriction as ?p) ?o ?g {
###                      graph ?g {
###                        ?s ccp:temp_possible_hgt_restriction ?o .
###                      }
###                    }
###                  }
#
#
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcb_assign_taxon/ncbi
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcb_assign_taxon/ncbi
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcb_assign_taxon/refseq
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcb_assign_taxon/refseq
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcb_assign_taxon/uniprot
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcb_assign_taxon/uniprot
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcb_assign_taxon/via_has_gene_template
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcb_assign_taxon/via_has_gene_template
#
##${BASE_SCRIPT_DIR}/RULES.sh rules/validation/bioworld_validation/taxon_validation/step_a
##${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/bioworld_validation/taxon_validation/step_a
##${BASE_SCRIPT_DIR}/RULES.sh rules/validation/bioworld_validation/taxon_validation/step_b
##${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/bioworld_validation/taxon_validation/step_b
##
##### todo: remove temporary links here
#### drop graph <file://validation_temporary-in-taxon-relations.nt>
#
######${BASE_SCRIPT_DIR}/RULES.sh rules/validation/bioworld_validation/taxon_validation/step_c
######${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/bioworld_validation/taxon_validation/step_c
##
###
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcc_generate_missing_ggp_entities/step_a
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcc_generate_missing_ggp_entities/step_a
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcc_generate_missing_ggp_entities/step_b
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcc_generate_missing_ggp_entities/step_b
##
###
###
##### todo: remove temporary links here
###
##drop graph <file://step-hcc_temporary_missing-ncrna-gen.nt>;
##drop graph <file://step-hcc_temporary_missing-rrna-gen.nt>;
##drop graph <file://step-hcc_temporary_missing-scrna-gen.nt>;
##drop graph <file://step-hcc_temporary_missing-snorna-gen.nt>;
##drop graph <file://step-hcc_temporary_missing-snrna-gen.nt>;
##drop graph <file://step-hcc_temporary_missing-trna-gen.nt>
###
###
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcd_generate_gene_abstractions
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hcd_generate_gene_abstractions
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hce_link_to_gp_abstractions
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hc_ggp_abstractions/step_hce_link_to_gp_abstractions
#####
######### linking
# =================================== #
# =================================== #
#          CLASS-BASED KR             #
# =================================== #
# =================================== #
### UNDER CONSTRUCTION ${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/biogrid/step_a_record_mentions_entity
### UNDER CONSTRUCTION ${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/biogrid/step_a_record_mentions_entity
##


# ====== BIOPLEX CLASS-BASED ======
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/bioplex/step_a
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/bioplex/step_a
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/bioplex/step_b
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/bioplex/step_b

# todo: remove temporary links here
##delete { graph ?g {?s rdfs:subClassOf ?o}} where {
##                    select ?s (ccp:temp_bioplex_interaction as ?o) ?g {
##                      graph ?g {
##                        ?s rdfs:subClassOf ccp:temp_bioplex_interaction .
##                      }
##                    }
##                  }

# ----- VALIDATION -----
#${BASE_SCRIPT_DIR}/RULES.sh rules/validation/valid_owl/restriction
#${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/valid_owl/restriction
##${BASE_SCRIPT_DIR}/RULES.sh rules/validation/valid_owl/list
##${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/valid_owl/list


# ===== DRUGBANK CLASS-BASED =====
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/drugbank/step_a
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/drugbank/step_a
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/drugbank/step_b
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/drugbank/step_b
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/drugbank/step_c
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/drugbank/step_c
### todo: remove temporary links here
### drop graph <file://step-hda_temp-link-drugbank-record-to-drug.nt>
##
##delete { graph ?g {?s rdfs:subClassOf ?o}} where {
##                    select ?s (ccp:temp_drugbank_interaction as ?o) ?g {
##                      graph ?g {
##                        ?s rdfs:subClassOf ccp:temp_drugbank_interaction .
##                      }
##                    }
##                  }

##delete { graph ?g {?s ?p ?o}} where {
##                    select ?s (ccp:temp_drug_participant as ?p) ?o ?g {
##                      graph ?g {
##                        ?s ccp:temp_drug_participant ?o .
##                      }
##                    }
##                  }

##delete { graph ?g {?s ?p ?o}} where {
##                    select ?s (ccp:temp_target_participant as ?p) ?o ?g {
##                      graph ?g {
##                        ?s ccp:temp_target_participant ?o .
##                      }
##                    }
##                  }

# ----- VALIDATION -----
#${BASE_SCRIPT_DIR}/RULES.sh rules/validation/valid_owl/restriction
#${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/valid_owl/restriction
##${BASE_SCRIPT_DIR}/RULES.sh rules/validation/valid_owl/list
##${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/valid_owl/list

# ===== GOA CLASS-BASED =====
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/goa/step_a
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/goa/step_a
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/goa/step_b
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/goa/step_b

## todo: remove temporary links here
##delete { graph ?g {?s rdfs:subClassOf ?o}} where {
##                    select ?s (ccp:temp_biological_process as ?o) ?g {
##                      graph ?g {
##                        ?s rdfs:subClassOf ccp:temp_biological_process .
##                      }
##                    }
##                  }

##delete { graph ?g {?s rdfs:subClassOf ?o}} where {
##                    select ?s (ccp:temp_localization_process as ?o) ?g {
##                      graph ?g {
##                        ?s rdfs:subClassOf ccp:temp_localization_process .
##                      }
##                    }
##                  }

##delete { graph ?g {?s rdfs:subClassOf ?o}} where {
##                    select ?s (ccp:temp_molecular_function as ?o) ?g {
##                      graph ?g {
##                        ?s rdfs:subClassOf ccp:temp_molecular_function .
##                      }
##                    }
##                  }

##delete { graph ?g {?s ?p ?o}} where {
##                    select ?s (ccp:temp_has_participant as ?p) ?o ?g {
##                      graph ?g {
##                        ?s ccp:temp_has_participant ?o .
##                      }
##                    }
##                  }


# ----- VALIDATION -----
#${BASE_SCRIPT_DIR}/RULES.sh rules/validation/valid_owl/restriction
#${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/valid_owl/restriction
##${BASE_SCRIPT_DIR}/RULES.sh rules/validation/valid_owl/list
##${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/valid_owl/list

# ===== HP CLASS-BASED =====
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/hp/step_a
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/hp/step_a
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/hp/step_b
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/hp/step_b

## todo - remove temporary links
##delete { graph ?g {?s rdfs:subClassOf ?o}} where {
##                    select ?s (ccp:temp_human_phenotype as ?o) ?g {
##                      graph ?g {
##                        ?s rdfs:subClassOf ccp:temp_human_phenotype .
##                      }
##                    }
##                  }

##delete { graph ?g {?s ?p ?o}} where {
##                    select ?s (ccp:temp_causes as ?p) ?o ?g {
##                      graph ?g {
##                        ?s ccp:temp_causes ?o .
##                      }
##                    }
##                  }

# ----- VALIDATION -----
#${BASE_SCRIPT_DIR}/RULES.sh rules/validation/valid_owl/restriction
#${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/valid_owl/restriction
##${BASE_SCRIPT_DIR}/RULES.sh rules/validation/valid_owl/list
##${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/valid_owl/list

# ===== IREFWEB CLASS-BASED =====
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/irefweb/step_a
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/irefweb/step_a
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/irefweb/step_b
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/irefweb/step_b

# todo - remove temporary links
##delete { graph ?g {?s rdfs:subClassOf ?o}} where {
##                    select ?s (ccp:temp_irefweb_binary_interaction as ?o) ?g {
##                      graph ?g {
##                        ?s rdfs:subClassOf ccp:temp_irefweb_binary_interaction .
##                      }
##                    }
##                  }

##delete { graph ?g {?s rdfs:subClassOf ?o}} where {
##                    select ?s (ccp:temp_irefweb_nary_interaction as ?o) ?g {
##                      graph ?g {
##                        ?s rdfs:subClassOf ccp:temp_irefweb_nary_interaction .
##                      }
##                    }
##                  }


# ----- VALIDATION -----
#${BASE_SCRIPT_DIR}/RULES.sh rules/validation/valid_owl/restriction
#${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/valid_owl/restriction
##${BASE_SCRIPT_DIR}/RULES.sh rules/validation/valid_owl/list
##${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/valid_owl/list



# ===== REACTOME CLASS-BASED =====
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/reactome/step_a_add_continuants_to_bio/step_a
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/reactome/step_a_add_continuants_to_bio/step_a
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/reactome/step_a_add_continuants_to_bio/step_b
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/reactome/step_a_add_continuants_to_bio/step_b

# todo - remove temporary links

# ----- VALIDATION -----
#${BASE_SCRIPT_DIR}/RULES.sh rules/validation/valid_owl/restriction
#${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/valid_owl/restriction
##${BASE_SCRIPT_DIR}/RULES.sh rules/validation/valid_owl/list
##${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/valid_owl/list
# TODO - write a validation rule to make sure all records that localize entities resulted in a localized entity


# ===== PHARMGKB CLASS-BASED =====-
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/pharmgkb/step_a
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/pharmgkb/step_a
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/pharmgkb/step_b
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/class_based_kr/pharmgkb/step_b

## todo - remove temporary links
##delete { graph ?g {?s rdfs:subClassOf ?o}} where {
##                    select ?s (ccp:temp_pharmgkb_interaction as ?o) ?g {
##                      graph ?g {
##                        ?s rdfs:subClassOf ccp:temp_pharmgkb_interaction .
##                      }
##                    }
##                  }

# ----- VALIDATION -----
#${BASE_SCRIPT_DIR}/RULES.sh rules/validation/valid_owl/restriction
#${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/valid_owl/restriction
##${BASE_SCRIPT_DIR}/RULES.sh rules/validation/valid_owl/list
##${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/valid_owl/list


######### bioworld expansion
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_i_bio_expansion/class_based_kr/step_a
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_i_bio_expansion/class_based_kr/step_a
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_i_bio_expansion/class_based_kr/step_b
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_i_bio_expansion/class_based_kr/step_b

## todo - remove temporary links
##delete { graph ?g {?s rdfs:subClassOf ?o}} where {
##                    select ?s (ccp:temp_location as ?o) ?g {
##                      graph ?g {
##                        ?s rdfs:subClassOf ccp:temp_location .
##                      }
##                    }
##                  }

# ----- VALIDATION -----
#${BASE_SCRIPT_DIR}/RULES.sh rules/validation/valid_owl/restriction
#${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/valid_owl/restriction
#${BASE_SCRIPT_DIR}/RULES.sh rules/validation/valid_owl/list
#${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/valid_owl/list


######### validation rules (only rule metadata triples added)
###${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_z_validation/subclass_hierarchy_checks



# =================================== #
# =================================== #
#          INSTANCE-BASED KR          #
# =================================== #
# =================================== #


# ====== BIOPLEX INSTANCE-BASED ======
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/bioplex/step_a
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/bioplex/step_a
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/bioplex/step_b
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/bioplex/step_b

# todo: remove temporary links here
##delete { graph ?g {?s rdfs:subClassOf ?o}} where {
##                    select ?s (ccp:temp_bioplex_interaction as ?o) ?g {
##                      graph ?g {
##                        ?s rdfs:subClassOf ccp:temp_bioplex_interaction .
##                      }
##                    }
##                  }

##delete { graph ?g {?s ?p ?o}} where {
##                    select ?s (ccp:temp_has_participant as ?p) ?o ?g {
##                      graph ?g {
##                        ?s ccp:temp_has_participant ?o .
##                      }
##                    }
##                  }

# ====== GOA INSTANCE-BASED ======
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/goa/step_a
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/goa/step_a
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/goa/step_b
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/goa/step_b

# todo: remove temporary links here
##delete { graph ?g {?s rdfs:subClassOf ?o}} where {
##                    select ?s (ccp:temp_biological_process as ?o) ?g {
##                      graph ?g {
##                        ?s rdfs:subClassOf ccp:temp_biological_process .
##                      }
##                    }
##                  }

##delete { graph ?g {?s rdfs:subClassOf ?o}} where {
##                    select ?s (ccp:temp_localization as ?o) ?g {
##                      graph ?g {
##                        ?s rdfs:subClassOf ccp:temp_localization .
##                      }
##                    }
##                  }

##delete { graph ?g {?s rdfs:subClassOf ?o}} where {
##                    select ?s (ccp:temp_molecular_function as ?o) ?g {
##                      graph ?g {
##                        ?s rdfs:subClassOf ccp:temp_biological_process .
##                      }
##                    }
##                  }


##delete { graph ?g {?s ?p ?o}} where {
##                    select ?s (ccp:temp_has_participant as ?p) ?o ?g {
##                      graph ?g {
##                        ?s ccp:temp_has_participant ?o .
##                      }
##                    }
##                  }

##delete { graph ?g {?s ?p ?o}} where {
##                    select ?s (ccp:temp_transports as ?p) ?o ?g {
##                      graph ?g {
##                        ?s ccp:temp_transports ?o .
##                      }
##                    }
##                  }

##delete { graph ?g {?s ?p ?o}} where {
##                    select ?s (ccp:temp_end_location as ?p) ?o ?g {
##                      graph ?g {
##                        ?s ccp:temp_end_location ?o .
##                      }
##                    }
##                  }

# ====== HP INSTANCE-BASED ======
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/hp/step_a
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/hp/step_a
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/hp/step_b
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/hp/step_b

# todo: remove temporary links here

##delete { graph ?g {?s rdfs:subClassOf ?o}} where {
##                    select ?s (ccp:temp_human_phenotype as ?o) ?g {
##                      graph ?g {
##                        ?s rdfs:subClassOf ccp:temp_human_phenotype .
##                      }
##                    }
##                  }

##delete { graph ?g {?s ?p ?o}} where {
##                    select ?s (ccp:temp_causes as ?p) ?o ?g {
##                      graph ?g {
##                        ?s ccp:temp_causes ?o .
##                      }
##                    }
##                  }


# ====== IREFWEB INSTANCE-BASED ======
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/irefweb/step_a
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/irefweb/step_a
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/irefweb/step_b
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/irefweb/step_b

# todo: remove temporary links here

##delete { graph ?g {?s rdfs:subClassOf ?o}} where {
##                    select ?s (ccp:temp_irefweb_nary_interaction as ?o) ?g {
##                      graph ?g {
##                        ?s rdfs:subClassOf ccp:temp_irefweb_nary_interaction .
##                      }
##                    }
##                  }

##delete { graph ?g {?s rdfs:subClassOf ?o}} where {
##                    select ?s (ccp:temp_irefweb_binary_interaction as ?o) ?g {
##                      graph ?g {
##                        ?s rdfs:subClassOf ccp:temp_irefweb_binary_interaction .
##                      }
##                    }
##                  }


##delete { graph ?g {?s ?p ?o}} where {
##                    select ?s (ccp:temp_has_participant as ?p) ?o ?g {
##                      graph ?g {
##                        ?s ccp:temp_has_participant ?o .
##                      }
##                    }
##                  }


# ====== PHARMGKB INSTANCE-BASED ======
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/pharmgkb/step_a
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/pharmgkb/step_a
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/pharmgkb/step_b
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/pharmgkb/step_b

# todo: remove temporary links here

##delete { graph ?g {?s rdfs:subClassOf ?o}} where {
##                    select ?s (ccp:temp_pharmgkb_interaction as ?o) ?g {
##                      graph ?g {
##                        ?s rdfs:subClassOf ccp:temp_pharmgkb_interaction .
##                      }
##                    }
##                  }

##delete { graph ?g {?s ?p ?o}} where {
##                    select ?s (ccp:temp_has_participant as ?p) ?o ?g {
##                      graph ?g {
##                        ?s ccp:temp_has_participant ?o .
##                      }
##                    }
##                  }


# ====== DRUGBANK INSTANCE-BASED ======
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/drugbank/step_a
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/drugbank/step_a
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/drugbank/step_b
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/drugbank/step_b
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/drugbank/step_c
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_h_ice_to_bio/step_hd_bioentity_linking/instance_based_kr/drugbank/step_c


# todo: remove temporary links here

### drop graph <file://step-hda_temp-link-drugbank-record-to-drug.nt>

##delete { graph ?g {?s rdfs:subClassOf ?o}} where {
##                    select ?s (ccp:temp_drugbank_interaction as ?o) ?g {
##                      graph ?g {
##                        ?s rdfs:subClassOf ccp:temp_drugbank_interaction .
##                      }
##                    }
##                  }

##delete { graph ?g {?s ?p ?o}} where {
##                    select ?s (ccp:temp_drug_participant as ?p) ?o ?g {
##                      graph ?g {
##                        ?s ccp:temp_drug_participant ?o .
##                      }
##                    }
##                  }

##delete { graph ?g {?s ?p ?o}} where {
##                    select ?s (ccp:temp_target_participant as ?p) ?o ?g {
##                      graph ?g {
##                        ?s ccp:temp_target_participant ?o .
##                      }
##                    }
##

# ----- STEP I INSTANCE-BASED -----
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_i_bio_expansion/instance_based_kr/step_a
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_i_bio_expansion/instance_based_kr/step_a
#${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_i_bio_expansion/instance_based_kr/step_b
#${BASE_SCRIPT_DIR}/LOAD.sh rules/_1_post_identifier_merge/step_i_bio_expansion/instance_based_kr/step_b


# ----- VALIDATION -----
#${BASE_SCRIPT_DIR}/RULES.sh rules/validation/valid_owl/restriction
#${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/valid_owl/restriction
#${BASE_SCRIPT_DIR}/RULES.sh rules/validation/valid_owl/list
#${BASE_SCRIPT_DIR}/LOAD.sh rules/validation/valid_owl/list
##
##
######### validation rules (only rule metadata triples added)
###${BASE_SCRIPT_DIR}/RULES.sh rules/_1_post_identifier_merge/step_z_validation/subclass_hierarchy_checks