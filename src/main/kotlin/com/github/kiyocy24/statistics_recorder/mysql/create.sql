CREATE TABLE `users` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `uuid` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `last_login` timestamp NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE
);

CREATE TABLE `statistic_logs` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` varchar(32) NOT NULL,
  `item_name` varchar(255) NOT NULL,
  `block_mined` bigint NOT NULL,
  `item_broken` bigint NOT NULL,
  `item_created` bigint NOT NULL,
  `item_used` bigint NOT NULL,
  `item_picked_up` bigint NOT NULL,
  `item_dropped` bigint NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE
);

ALTER TABLE `statistic_logs` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
