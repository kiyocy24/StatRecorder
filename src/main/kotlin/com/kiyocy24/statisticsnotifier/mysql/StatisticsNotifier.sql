CREATE TABLE `users` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `uuid` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `last_login` timestamp,
  `created_at` timestamp NOT NULL DEFAULT (now()),
  `updated_at` timestamp NOT NULL DEFAULT (now())
);

CREATE TABLE `statistic_logs` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `date_at` date NOT NULL,
  `item_name` varchar(255) NOT NULL,
  `block_mined_num` uint NOT NULL,
  `item_broken_num` uint NOT NULL,
  `item_created_num` uint NOT NULL,
  `item_used_num` uint NOT NULL,
  `item_picked_up_num` uint NOT NULL,
  `item_dropped_num` uint NOT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL
);

CREATE TABLE `entity_kill_logs` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `date_at` date NOT NULL,
  `entity_name` varchar(255) NOT NULL,
  `kill_num` uint NOT NULL,
  `killed_num` uint NOT NULL
);

ALTER TABLE `statistic_logs` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `entity_kill_logs` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
