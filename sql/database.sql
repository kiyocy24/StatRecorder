CREATE TABLE `users` (
  `id` int unsigned PRIMARY KEY AUTO_INCREMENT,
  `uuid` varchar(32) NOT NULL,
  `name` varchar(255) NOT NULL,
  `last_login` timestamp,
  `created_at` timestamp NOT NULL DEFAULT (now()),
  `updated_at` timestamp NOT NULL DEFAULT (now())
);

CREATE TABLE `statistic_logs` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int unsigned,
  `date_at` date NOT NULL,
  `block_mined` int unsigned NOT NULL,
  `item_broken` int unsigned NOT NULL,
  `item_created` int unsigned NOT NULL,
  `item_used` int unsigned NOT NULL,
  `item_picked_up` int unsigned NOT NULL,
  `item_dropped` int unsigned NOT NULL,
  `entity_killed` int unsigned NOT NULL,
  `entity_killed_by` int unsigned NOT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL
);

ALTER TABLE `statistic_logs` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
