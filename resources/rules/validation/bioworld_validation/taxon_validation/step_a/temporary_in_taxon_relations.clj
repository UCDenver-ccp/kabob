`{:name        "validation_temporary-in-taxon-relations"
  :description "Checks for bioentities that are subClassOf multiple taxonomy restrictions that reference different taxonomies."
  :head        ((?/bioentity ccp/temp_in_taxon ?/taxon))
  :body
               "prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
               PREFIX obo: <http://purl.obolibrary.org/obo/>
               select distinct ?bioentity ?taxon {

                   {
                    select ?only_in_taxon {
                                           kice:RO_0002160 obo:IAO_0000219 ?only_in_taxon .
                                           filter (?only_in_taxon != obo:RO_0002160) .
                                           }
                    }

                   ?r owl:onProperty ?only_in_taxon .
                   ?r owl:someValuesFrom ?taxon .
                   ?bioentity rdfs:subClassOf ?r .

               }"
  }
