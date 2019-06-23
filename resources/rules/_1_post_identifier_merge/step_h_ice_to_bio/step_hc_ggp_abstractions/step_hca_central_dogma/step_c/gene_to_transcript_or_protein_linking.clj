;; -----------------------------------------------------------------
;; --------- Ensembl Gene ID NCBI Gene ID Exact Match --------
;; -----------------------------------------------------------------
`{:name "step-hcac_transcript_or_protein_to_gene_linking"
  :description "This rule links transcripts and proteins with genes using has_gene_template"
  :head ((?/has_gene_template_restriction rdf/type owl/Restriction)
          (?/has_gene_template_restriction owl/onProperty ?/has_gene_template)
          (?/has_gene_template_restriction owl/someValuesFrom ?/gene)
          (?/transcript_or_protein rdfs/subClassOf ?/has_gene_template_restriction))
  :reify ([?/has_gene_template_restriction {:ln (:restriction)
                                            :ns "kbio" :prefix "RS_"}])
  :body "prefix franzOption_chunkProcessingAllowed: <franz:yes>
  prefix franzOption_clauseReorderer: <franz:identity>
  PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX obo_pr: <http://purl.obolibrary.org/obo/pr#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
    SELECT distinct ?gene ?transcript_or_protein ?has_gene_template
  WHERE {

         {
          select ?has_gene_template {
                                     ?has_gene_template_id obo:IAO_0000219 <http://purl.obolibrary.org/obo/pr#has_gene_template> .
                                                           ?has_gene_template_id obo:IAO_0000219 ?has_gene_template .
                                                           # ensure it's a kabob bioentity (not an obo bioentity)
                                     filter (contains (str(?has_gene_template), 'http://ccp.ucdenver.edu/kabob/bio/'))
                                     }
          }

         ?transcript_or_protein ccp:temp_possible_hgt_restriction ?gene .

         # don't duplicate existing has_gene_template restrictions
         filter not exists {
                            ?transcript_or_protein rdfs:subClassOf ?r .
                            ?r owl:onProperty ?has_gene_template .
                            ?r owl:someValuesFrom ?gene .
                            }
         }"
}