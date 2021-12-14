select get_address_by_language(place_id, NULL,  ARRAY['name:en', 'name', 'alt_name']) AS en_label from placex where class='building' and type = 'yes' limit 10;

select get_address_by_language(place_id, NULL,  ARRAY['name:en', 'name', 'alt_name']) AS en_label from placex where class='building' and type = 'house' limit 10;

select get_address_by_language(place_id, NULL,  ARRAY['name:en', 'name', 'alt_name']) AS en_label from placex where class='highway' and type = 'residential' limit 10;