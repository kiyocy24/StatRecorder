package com.github.kiyocy24.statistics_recorder.infrastructure

const val CREATE_USERS = """
CREATE TABLE IF NOT EXISTS `users` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `uuid` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `last_login` timestamp NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
"""

const val CREATE_STATISTICS_LOGS = """
CREATE TABLE IF NOT EXISTS `statistics_logs` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `item_name` varchar(255) NOT NULL,
  `block_mined` bigint NOT NULL,
  `item_broken` bigint NOT NULL,
  `item_created` bigint NOT NULL,
  `item_used` bigint NOT NULL,
  `item_picked_up` bigint NOT NULL,
  `item_dropped` bigint NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
"""