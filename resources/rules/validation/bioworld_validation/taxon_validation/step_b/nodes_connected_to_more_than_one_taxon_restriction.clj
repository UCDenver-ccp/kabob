`{:name        "validation_nodes-connected-to-more-than-one-taxon-restriction-EXPECT-0"
  :description "Checks for bioentities that are subClassOf multiple taxonomy restrictions that reference different taxonomies."
  :head        ()
  :body
               "prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
               PREFIX obo: <http://purl.obolibrary.org/obo/>
               select distinct ?bioentity {

                   ?bioentity ccp:temp_in_taxon ?taxon1 .
                   ?bioentity ccp:temp_in_taxon ?taxon2 .
                   filter (?taxon1 != ?taxon2)

               }"
  }
