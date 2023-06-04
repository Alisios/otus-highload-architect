CREATE UNIQUE INDEX CONCURRENTLY IF NOT EXISTS friendid_userid_idx ON social_network.friends (user_id, friend_id);
