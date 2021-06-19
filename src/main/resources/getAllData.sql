SELECT m.electricity        as electricity,
       m.hot_water_bath     as hw_bath,
       m.cold_water_bath    as cw_bath,
       m.hot_water_kitchen  as hw_kitchen,
       m.cold_water_kitchen as cw_kitchen,
       m.date               as date,

       t1.electricity       as t_electricity,
       t1.hot_water         as t_hw,
       t1.cold_water        as t_cw,
       t1.drainage          as t_drainage

FROM month_data as m
         left join tariffs as t1
              on t1.tariff_id =
                 (SELECT t2.tariff_id
                  FROM tariffs as t2
                  WHERE m.date >= t2.init_date
                  ORDER BY t2.init_date DESC
                  LIMIT 1)

ORDER BY date