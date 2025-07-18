-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jul 18, 2025 at 08:02 PM
-- Server version: 8.0.30
-- PHP Version: 8.2.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `todo_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `asset`
--

CREATE TABLE `asset` (
  `id` bigint NOT NULL,
  `harga` double DEFAULT NULL,
  `jenis_aset` enum('KENDARAAN','ELEKTRONIK','FURNITURE') NOT NULL,
  `nama_aset` varchar(255) DEFAULT NULL,
  `qty` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `asset`
--

INSERT INTO `asset` (`id`, `harga`, `jenis_aset`, `nama_aset`, `qty`) VALUES
(9, 10, 'ELEKTRONIK', 'hp', 12),
(11, 1000000, 'KENDARAAN', 'BRIO', 10);

-- --------------------------------------------------------

--
-- Table structure for table `stock_opname`
--

CREATE TABLE `stock_opname` (
  `id` bigint NOT NULL,
  `keterangan` varchar(255) DEFAULT NULL,
  `stok_fisik` int NOT NULL,
  `stok_sistem` int NOT NULL,
  `tanggal` date DEFAULT NULL,
  `asset_id` bigint DEFAULT NULL,
  `real_stock` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `stock_opname`
--

INSERT INTO `stock_opname` (`id`, `keterangan`, `stok_fisik`, `stok_sistem`, `tanggal`, `asset_id`, `real_stock`) VALUES
(1, 'Tidak Sesuai', 11, 12, '2025-07-19', 9, 0),
(2, 'Sesuai', 12, 12, '2025-07-19', 9, 0),
(3, 'Tidak Sesuai', 1, 10, '2025-07-19', 11, 0),
(4, 'Tidak Sesuai', 12, 10, '2025-07-19', 11, 0),
(5, 'Tidak Sesuai', 1, 12, '2025-07-19', 9, 0),
(6, 'Sesuai', 10, 10, '2025-07-19', 11, 0),
(7, 'Tidak Sesuai', 1, 12, '2025-07-19', 9, 0);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `email`, `role`) VALUES
(1, 'admin', '$2a$10$ccqAe0gAeiafxC4I52TQAOxwUReaIUgE1nvsFXBQZXRv50FZ/5zZu', 'admin@gmail.com', 'USER'),
(2, 'anif', '$2a$10$12hNK0GoEfMdCzdakmf0r.yPH8m71f0uLqUvK7p29kyQKsTG81Wf6', '12@gmail.com', 'ADMIN'),
(3, 'burhan', '$2a$10$kyyhrvzbZZveZDrXoOq6fu6B0mw.5v8ndlHUe3i/5DTsmQ8EGT5f2', 'burhan@gmail.com', 'USER');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `asset`
--
ALTER TABLE `asset`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `stock_opname`
--
ALTER TABLE `stock_opname`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKg0pru2bq9jjv1e8o447iiy0sd` (`asset_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `asset`
--
ALTER TABLE `asset`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `stock_opname`
--
ALTER TABLE `stock_opname`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `stock_opname`
--
ALTER TABLE `stock_opname`
  ADD CONSTRAINT `FKg0pru2bq9jjv1e8o447iiy0sd` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
