-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 22, 2024 lúc 03:03 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `snakegame`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `food`
--

CREATE TABLE `food` (
  `idFood` varchar(3) NOT NULL,
  `nameFood` varchar(20) NOT NULL,
  `pathImage` varchar(100) NOT NULL,
  `ScoreFood` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `food`
--

INSERT INTO `food` (`idFood`, `nameFood`, `pathImage`, `ScoreFood`) VALUES
('F1', 'Apple', 'C:\\xampp\\htdocs\\MyProject\\image\\food\\Apple.png', 5),
('F2', 'Banana', 'C:\\xampp\\htdocs\\MyProject\\image\\food\\Banana.png', 10),
('F3', 'Cherry', 'C:\\xampp\\htdocs\\MyProject\\image\\food\\Cherry.png', 15),
('F4', 'Mulberry', 'C:\\xampp\\htdocs\\MyProject\\image\\food\\Mulberry.png', 12),
('F6', 'Orange', 'C:\\xampp\\htdocs\\MyProject\\image\\food\\Orange.png', 16),
('F7', 'Pineapple', 'C:\\xampp\\htdocs\\MyProject\\image\\food\\Pineapple.png', 18),
('F8', 'Watermelon', 'C:\\xampp\\htdocs\\MyProject\\image\\food\\Watermelon.png', 20),
('F9', 'Lemon', 'C:\\xampp\\htdocs\\MyProject\\image\\food\\Lemon.png', 9);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `map`
--

CREATE TABLE `map` (
  `IDMap` int(11) NOT NULL,
  `Border` varchar(120) NOT NULL,
  `Backround1` varchar(120) NOT NULL,
  `Backround2` varchar(120) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `map`
--

INSERT INTO `map` (`IDMap`, `Border`, `Backround1`, `Backround2`) VALUES
(1, 'C:\\xampp\\htdocs\\MyProject\\image\\map\\map1\\Border.png', 'C:\\xampp\\htdocs\\MyProject\\image\\map\\map1\\Backround1.png', 'C:\\xampp\\htdocs\\MyProject\\image\\map\\map1\\Backround2.png');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `potions`
--

CREATE TABLE `potions` (
  `idPotion` varchar(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `pathImage` varchar(255) NOT NULL,
  `info` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `potions`
--

INSERT INTO `potions` (`idPotion`, `Name`, `pathImage`, `info`) VALUES
('P1', 'Potion X2 Score', 'C:\\xampp\\htdocs\\MyProject\\image\\Potion\\X2Score.png', 'nhân đôi điểm hiện tại'),
('P2', 'Potion Decrease Move', 'C:\\xampp\\htdocs\\MyProject\\image\\Potion\\DeIncrease.png', 'giảm tốc độ của rắn'),
('P3', 'Potion Bad Effect', 'C:\\xampp\\htdocs\\MyProject\\image\\Potion\\BadEffect.png', 'thân rắn dài thêm 3');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `HighScore` int(10) NOT NULL,
  `phoneNumber` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`HighScore`, `phoneNumber`) VALUES
(536, '0355957687'),
(2913, '0358573235'),
(773, '0967107330');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `food`
--
ALTER TABLE `food`
  ADD PRIMARY KEY (`idFood`);

--
-- Chỉ mục cho bảng `map`
--
ALTER TABLE `map`
  ADD PRIMARY KEY (`IDMap`);

--
-- Chỉ mục cho bảng `potions`
--
ALTER TABLE `potions`
  ADD PRIMARY KEY (`idPotion`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`phoneNumber`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `map`
--
ALTER TABLE `map`
  MODIFY `IDMap` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
