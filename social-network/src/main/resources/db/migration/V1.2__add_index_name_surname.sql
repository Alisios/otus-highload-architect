create index IF NOT EXISTS ind_name_prefix on social_network.users(name text_pattern_ops, surname text_pattern_ops);
