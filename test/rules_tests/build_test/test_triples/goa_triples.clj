(ns rules-tests.build-test.test-triples.goa-triples)



(def goa-triples '((kice/RS_GOA_20101217 rdf/type ccp/IAO_EXT_0000012)
                    (kice/RS_GOA_20101217 obo/BFO_0000051 kice/R_N1seX8D14o8bZcRtKqqzycEuXQk)
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk rdf/type ccp/IAO_EXT_0000007)
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/R_zRZLn7ppOeB6N0Cw8GAjUhcMHzU)
                    (kice/R_zRZLn7ppOeB6N0Cw8GAjUhcMHzU rdf/type ccp/IAO_EXT_0001634)
                    (kice/R_zRZLn7ppOeB6N0Cw8GAjUhcMHzU obo/BFO_0000051 kice/F_nkIzcWI_W3r3EfEWKlWqr8_u_xA)
                    (kice/F_nkIzcWI_W3r3EfEWKlWqr8_u_xA rdf/type ccp/IAO_EXT_0001637)
                    (kice/F_nkIzcWI_W3r3EfEWKlWqr8_u_xA rdf/type kice/UBERON_0008307)
                    (kice/F_nkIzcWI_W3r3EfEWKlWqr8_u_xA rdfs/label ["UBERON:0008307" "en"])
                    (kice/UBERON_0008307 rdfs/subClassOf ccp/IAO_EXT_0000201)
                    (kice/R_zRZLn7ppOeB6N0Cw8GAjUhcMHzU obo/BFO_0000051 kice/F_JGkPsks4cifTCKmeBtdZEKihwt8)
                    (kice/F_JGkPsks4cifTCKmeBtdZEKihwt8 rdf/type ccp/IAO_EXT_0001636)
                    (kice/F_JGkPsks4cifTCKmeBtdZEKihwt8 rdfs/label ["occurs_in" "en"])
                    (kice/R_zRZLn7ppOeB6N0Cw8GAjUhcMHzU rdf/type ccp/IAO_EXT_0000027)
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_C--8rlrAdT1tbhqKUNtK5vnoab8)
                    (kice/F_C--8rlrAdT1tbhqKUNtK5vnoab8 rdf/type ccp/IAO_EXT_0000018)
                    (kice/F_C--8rlrAdT1tbhqKUNtK5vnoab8 rdfs/label ["P" "en"])
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_0fps6YBQtKFY-sAwfQNz0VHItwc)
                    (kice/F_0fps6YBQtKFY-sAwfQNz0VHItwc rdf/type ccp/IAO_EXT_0000026)
                    (kice/F_0fps6YBQtKFY-sAwfQNz0VHItwc rdfs/label ["BHF-UCL" "en"])
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_3zG-3h3NKEZNgd8Nenv1motmrtE)
                    (kice/F_3zG-3h3NKEZNgd8Nenv1motmrtE rdf/type ccp/IAO_EXT_0000009)
                    (kice/F_3zG-3h3NKEZNgd8Nenv1motmrtE rdfs/label ["UniProtKB" "en"])
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_ef8kJcDH6A9k_yuKl3I3hUz5vyA)
                    (kice/F_ef8kJcDH6A9k_yuKl3I3hUz5vyA rdf/type ccp/IAO_EXT_0000025)
                    (kice/F_ef8kJcDH6A9k_yuKl3I3hUz5vyA rdfs/label ["java.util.GregorianCalendar[time=1464760800000,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id=\"America/Denver\",offset=-25200000,dstSavings=3600000,useDaylight=true,transitions=157,lastRule=java.util.SimpleTimeZone[id=America/Denver,offset=-25200000,dstSavings=3600000,useDaylight=true,startYear=0,startMode=3,startMonth=2,startDay=8,startDayOfWeek=1,startTime=7200000,startTimeMode=0,endMode=3,endMonth=10,endDay=1,endDayOfWeek=1,endTime=7200000,endTimeMode=0]],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2016,MONTH=5,WEEK_OF_YEAR=23,WEEK_OF_MONTH=1,DAY_OF_MONTH=1,DAY_OF_YEAR=153,DAY_OF_WEEK=4,DAY_OF_WEEK_IN_MONTH=1,AM_PM=0,HOUR=0,HOUR_OF_DAY=0,MINUTE=0,SECOND=0,MILLISECOND=0,ZONE_OFFSET=-25200000,DST_OFFSET=3600000" "en"])
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_P1D4Hw0wiZvuIK4wV_EFDNpemvY)
                    (kice/F_P1D4Hw0wiZvuIK4wV_EFDNpemvY rdf/type ccp/IAO_EXT_0000010)
                    (kice/F_P1D4Hw0wiZvuIK4wV_EFDNpemvY rdf/type kice/UNIPROT_P37173)
                    (kice/F_P1D4Hw0wiZvuIK4wV_EFDNpemvY rdfs/label ["P37173" "en"])
                    (kice/UNIPROT_P37173 rdfs/subClassOf ccp/IAO_EXT_0000184)
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_Tn5-q1XHkczKxGAhtt6E3wG17dw)
                    (kice/F_Tn5-q1XHkczKxGAhtt6E3wG17dw rdf/type ccp/IAO_EXT_0000022)
                    (kice/F_Tn5-q1XHkczKxGAhtt6E3wG17dw rdfs/label ["TGF-beta receptor type-2" "en"])
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_7tovQMzvkA3VJ5pmEIEeualVaSA)
                    (kice/F_7tovQMzvkA3VJ5pmEIEeualVaSA rdf/type ccp/IAO_EXT_0000011)
                    (kice/F_7tovQMzvkA3VJ5pmEIEeualVaSA rdfs/label ["TGFBR2" "en"])
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_ZaxrXsR1Lii8paWNFGmStOALI70)
                    (kice/F_ZaxrXsR1Lii8paWNFGmStOALI70 rdf/type ccp/IAO_EXT_0000021)
                    (kice/F_ZaxrXsR1Lii8paWNFGmStOALI70 rdfs/label ["A" "en"])
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_lctW_cuGE2lHs3PwmRin9vxVo18)
                    (kice/F_lctW_cuGE2lHs3PwmRin9vxVo18 rdf/type ccp/IAO_EXT_0000021)
                    (kice/F_lctW_cuGE2lHs3PwmRin9vxVo18 rdfs/label ["B" "en"])
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_5VOaZ8AN0IU4WOAXy4QMG75QulI)
                    (kice/F_5VOaZ8AN0IU4WOAXy4QMG75QulI rdf/type ccp/IAO_EXT_0000021)
                    (kice/F_5VOaZ8AN0IU4WOAXy4QMG75QulI rdfs/label ["F" "en"])
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_kwtKFskjThPH50GZs8RmlsVlCQc)
                    (kice/F_kwtKFskjThPH50GZs8RmlsVlCQc rdf/type ccp/IAO_EXT_0000021)
                    (kice/F_kwtKFskjThPH50GZs8RmlsVlCQc rdfs/label ["G" "en"])
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_a2uq9Q2YaPCTw5MNX1LADR8-Uwk)
                    (kice/F_a2uq9Q2YaPCTw5MNX1LADR8-Uwk rdf/type ccp/IAO_EXT_0000021)
                    (kice/F_a2uq9Q2YaPCTw5MNX1LADR8-Uwk rdfs/label ["H" "en"])
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_jwSaTPC22L3ymGqX9YcYwNJgwW0)
                    (kice/F_jwSaTPC22L3ymGqX9YcYwNJgwW0 rdf/type ccp/IAO_EXT_0000021)
                    (kice/F_jwSaTPC22L3ymGqX9YcYwNJgwW0 rdfs/label ["M" "en"])
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_CwUkajjEinjH7eUQb2QCtAS8t44)
                    (kice/F_CwUkajjEinjH7eUQb2QCtAS8t44 rdf/type ccp/IAO_EXT_0000021)
                    (kice/F_CwUkajjEinjH7eUQb2QCtAS8t44 rdfs/label ["N" "en"])
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_mgNOZnc34I6pGxrpea_ldh6D6DU)
                    (kice/F_mgNOZnc34I6pGxrpea_ldh6D6DU rdf/type ccp/IAO_EXT_0000021)
                    (kice/F_mgNOZnc34I6pGxrpea_ldh6D6DU rdfs/label ["R" "en"])
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_GRURM-WoEdE5Hfub8f8-kucDfCQ)
                    (kice/F_GRURM-WoEdE5Hfub8f8-kucDfCQ rdf/type ccp/IAO_EXT_0000021)
                    (kice/F_GRURM-WoEdE5Hfub8f8-kucDfCQ rdfs/label ["2" "en"])
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_OGQaQARvOWzKxlOQn4TM0upr3oM)
                    (kice/F_OGQaQARvOWzKxlOQn4TM0upr3oM rdf/type ccp/IAO_EXT_0000021)
                    (kice/F_OGQaQARvOWzKxlOQn4TM0upr3oM rdfs/label ["T" "en"])
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_xJRII-Fr3fyi9fhX0usGRGsmTms)
                    (kice/F_xJRII-Fr3fyi9fhX0usGRGsmTms rdf/type ccp/IAO_EXT_0000021)
                    (kice/F_xJRII-Fr3fyi9fhX0usGRGsmTms rdfs/label ["U" "en"])
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_mrPyty0hz1G8ERXNDLlIwZjBibs)
                    (kice/F_mrPyty0hz1G8ERXNDLlIwZjBibs rdf/type ccp/IAO_EXT_0000021)
                    (kice/F_mrPyty0hz1G8ERXNDLlIwZjBibs rdfs/label ["|" "en"])
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_ZVww4bWidxFhDCQnLPUWKBSD4xY)
                    (kice/F_ZVww4bWidxFhDCQnLPUWKBSD4xY rdf/type ccp/IAO_EXT_0000021)
                    (kice/F_ZVww4bWidxFhDCQnLPUWKBSD4xY rdfs/label ["_" "en"])
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_HO77_5qeIaowagO6jS-_WSBXELg)
                    (kice/F_HO77_5qeIaowagO6jS-_WSBXELg rdf/type ccp/IAO_EXT_0000024)
                    (kice/F_HO77_5qeIaowagO6jS-_WSBXELg rdf/type kice/NCBITaxon_9606)
                    (kice/F_HO77_5qeIaowagO6jS-_WSBXELg rdfs/label 9606)
                    (kice/NCBITaxon_9606 rdfs/subClassOf ccp/IAO_EXT_0000204)
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_U_azpBgUvYZl2vtVziakOb7BzwM)
                    (kice/F_U_azpBgUvYZl2vtVziakOb7BzwM rdf/type ccp/IAO_EXT_0000023)
                    (kice/F_U_azpBgUvYZl2vtVziakOb7BzwM rdfs/label ["protein" "en"])
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_CT73yHiCBufhvaZYHt3DuQuzU7E)
                    (kice/F_CT73yHiCBufhvaZYHt3DuQuzU7E rdf/type ccp/IAO_EXT_0000016)
                    (kice/F_CT73yHiCBufhvaZYHt3DuQuzU7E rdfs/label ["ISS" "en"])
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_pxbcAim6huDS6lujw36pgZ2NqSg)
                    (kice/F_pxbcAim6huDS6lujw36pgZ2NqSg rdf/type ccp/IAO_EXT_0000014)
                    (kice/F_pxbcAim6huDS6lujw36pgZ2NqSg rdf/type kice/GO_0019226)
                    (kice/F_pxbcAim6huDS6lujw36pgZ2NqSg rdfs/label ["GO:0019226" "en"])
                    (kice/GO_0019226 rdfs/subClassOf ccp/IAO_EXT_0000102)
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_uVW-eN8FsBaCJG0ukAfo4TMHAAg)
                    (kice/F_uVW-eN8FsBaCJG0ukAfo4TMHAAg rdf/type ccp/IAO_EXT_0000015)
                    (kice/F_uVW-eN8FsBaCJG0ukAfo4TMHAAg rdf/type kice/GO_REFERENCE_GO_REF_0000024)
                    (kice/F_uVW-eN8FsBaCJG0ukAfo4TMHAAg rdfs/label ["GO_REF:0000024" "en"])
                    (kice/GO_REFERENCE_GO_REF_0000024 rdfs/subClassOf ccp/IAO_EXT_0001511)
                    (kice/R_N1seX8D14o8bZcRtKqqzycEuXQk obo/BFO_0000051 kice/F_5-I0dfZ9JVWVeKYITJIaDVVz3VM)
                    (kice/F_5-I0dfZ9JVWVeKYITJIaDVVz3VM rdf/type ccp/IAO_EXT_0000017)
                    (kice/F_5-I0dfZ9JVWVeKYITJIaDVVz3VM rdf/type kice/UNIPROT_Q62312)
                    (kice/F_5-I0dfZ9JVWVeKYITJIaDVVz3VM rdfs/label ["Q62312" "en"])
                    (kice/UNIPROT_Q62312 rdfs/subClassOf ccp/IAO_EXT_0000184)
                    (kice/RS_GOA_20101217 obo/BFO_0000051 kice/R_t_VvyfHSPhApBPedf7tEg_URBGk)
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk rdf/type ccp/IAO_EXT_0000007)
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_lqTkhrGUSmF657-f6Gx5H8RPrZw)
                    (kice/F_lqTkhrGUSmF657-f6Gx5H8RPrZw rdf/type ccp/IAO_EXT_0000018)
                    (kice/F_lqTkhrGUSmF657-f6Gx5H8RPrZw rdfs/label ["C" "en"])
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_jGbBcRF70zGytQHSQEawsyY2Nhk)
                    (kice/F_jGbBcRF70zGytQHSQEawsyY2Nhk rdf/type ccp/IAO_EXT_0000026)
                    (kice/F_jGbBcRF70zGytQHSQEawsyY2Nhk rdfs/label ["UniProt" "en"])
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_3zG-3h3NKEZNgd8Nenv1motmrtE)
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_iQ_JQhdivPnhwCYqFKAjpPEk1eY)
                    (kice/F_iQ_JQhdivPnhwCYqFKAjpPEk1eY rdf/type ccp/IAO_EXT_0000025)
                    (kice/F_iQ_JQhdivPnhwCYqFKAjpPEk1eY rdfs/label ["java.util.GregorianCalendar[time=1452582000000,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id=\"America/Denver\",offset=-25200000,dstSavings=3600000,useDaylight=true,transitions=157,lastRule=java.util.SimpleTimeZone[id=America/Denver,offset=-25200000,dstSavings=3600000,useDaylight=true,startYear=0,startMode=3,startMonth=2,startDay=8,startDayOfWeek=1,startTime=7200000,startTimeMode=0,endMode=3,endMonth=10,endDay=1,endDayOfWeek=1,endTime=7200000,endTimeMode=0]],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2016,MONTH=0,WEEK_OF_YEAR=3,WEEK_OF_MONTH=3,DAY_OF_MONTH=12,DAY_OF_YEAR=12,DAY_OF_WEEK=3,DAY_OF_WEEK_IN_MONTH=2,AM_PM=0,HOUR=0,HOUR_OF_DAY=0,MINUTE=0,SECOND=0,MILLISECOND=0,ZONE_OFFSET=-25200000,DST_OFFSET=0]" "en"])
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_P1D4Hw0wiZvuIK4wV_EFDNpemvY)
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_Tn5-q1XHkczKxGAhtt6E3wG17dw)
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_7tovQMzvkA3VJ5pmEIEeualVaSA)
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_ZaxrXsR1Lii8paWNFGmStOALI70)
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_lctW_cuGE2lHs3PwmRin9vxVo18)
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_5VOaZ8AN0IU4WOAXy4QMG75QulI)
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_kwtKFskjThPH50GZs8RmlsVlCQc)
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_a2uq9Q2YaPCTw5MNX1LADR8-Uwk)
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_jwSaTPC22L3ymGqX9YcYwNJgwW0)
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_CwUkajjEinjH7eUQb2QCtAS8t44)
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_mgNOZnc34I6pGxrpea_ldh6D6DU)
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_GRURM-WoEdE5Hfub8f8-kucDfCQ)
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_OGQaQARvOWzKxlOQn4TM0upr3oM)
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_xJRII-Fr3fyi9fhX0usGRGsmTms)
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_mrPyty0hz1G8ERXNDLlIwZjBibs)
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_ZVww4bWidxFhDCQnLPUWKBSD4xY)
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_HO77_5qeIaowagO6jS-_WSBXELg)
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_U_azpBgUvYZl2vtVziakOb7BzwM)
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_bJJyR2pSC1YE56i8SjckG9Bv9mg)
                    (kice/F_bJJyR2pSC1YE56i8SjckG9Bv9mg rdf/type ccp/IAO_EXT_0000016)
                    (kice/F_bJJyR2pSC1YE56i8SjckG9Bv9mg rdfs/label ["IDA" "en"])
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_vg5IsaeZk6uohxk-qmWN1M6LSYA)
                    (kice/F_vg5IsaeZk6uohxk-qmWN1M6LSYA rdf/type ccp/IAO_EXT_0000014)
                    (kice/F_vg5IsaeZk6uohxk-qmWN1M6LSYA rdf/type kice/GO_0005623)
                    (kice/F_vg5IsaeZk6uohxk-qmWN1M6LSYA rdfs/label ["GO:0005623" "en"])
                    (kice/GO_0005623 rdfs/subClassOf ccp/IAO_EXT_0000102)
                    (kice/R_t_VvyfHSPhApBPedf7tEg_URBGk obo/BFO_0000051 kice/F_eHIN4XM8jQQPNj4ddmKhwDFt6ww)
                    (kice/F_eHIN4XM8jQQPNj4ddmKhwDFt6ww rdf/type ccp/IAO_EXT_0000015)
                    (kice/F_eHIN4XM8jQQPNj4ddmKhwDFt6ww rdf/type kice/PUBMED_25893292)
                    (kice/F_eHIN4XM8jQQPNj4ddmKhwDFt6ww rdfs/label 25893292)
                    (kice/PUBMED_25893292 rdfs/subClassOf ccp/IAO_EXT_0000274)))
