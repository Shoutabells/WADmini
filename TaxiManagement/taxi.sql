-- phpMyAdmin SQL Dump
-- version 4.8.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 07, 2018 at 07:30 AM
-- Server version: 10.1.33-MariaDB
-- PHP Version: 7.2.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `taxi`
--

-- --------------------------------------------------------

--
-- Table structure for table `drivertable`
--

CREATE TABLE `drivertable` (
  `Id` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Contact` varchar(15) NOT NULL,
  `Address` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `drivertable`
--

INSERT INTO `drivertable` (`Id`, `Name`, `Contact`, `Address`) VALUES
(1, 'mark', '9987888787', 'xyz xyz xyz ');

-- --------------------------------------------------------

--
-- Table structure for table `expensetable`
--

CREATE TABLE `expensetable` (
  `Id` int(11) NOT NULL,
  `Date` datetime DEFAULT NULL,
  `Description` varchar(100) DEFAULT NULL,
  `TaxiNum` varchar(100) DEFAULT NULL,
  `Driver` varchar(100) DEFAULT NULL,
  `Amount` double NOT NULL,
  `TId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `expensetable`
--

INSERT INTO `expensetable` (`Id`, `Date`, `Description`, `TaxiNum`, `Driver`, `Amount`, `TId`) VALUES
(1, '2018-09-07 00:00:00', 'xyz', '123254', 'mark', 500, 2);

-- --------------------------------------------------------

--
-- Table structure for table `ordertable`
--

CREATE TABLE `ordertable` (
  `Id` int(11) NOT NULL,
  `Date` datetime DEFAULT NULL,
  `TaxiNum` varchar(100) DEFAULT NULL,
  `Customer_Name` varchar(100) DEFAULT NULL,
  `Pickup_Add` varchar(200) DEFAULT NULL,
  `Destination_Add` varchar(200) DEFAULT NULL,
  `Amount` double NOT NULL,
  `TId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ordertable`
--

INSERT INTO `ordertable` (`Id`, `Date`, `TaxiNum`, `Customer_Name`, `Pickup_Add`, `Destination_Add`, `Amount`, `TId`) VALUES
(1, '2018-09-07 00:00:00', '32154', 'mark', 'xyz', 'xyz ', 100, 1);

-- --------------------------------------------------------

--
-- Table structure for table `taxitable`
--

CREATE TABLE `taxitable` (
  `Id` int(11) NOT NULL,
  `Number` varchar(120) NOT NULL,
  `Make` varchar(15) NOT NULL,
  `Model` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `taxitable`
--

INSERT INTO `taxitable` (`Id`, `Number`, `Make`, `Model`) VALUES
(1, '32154', 'maruti', '800');

-- --------------------------------------------------------

--
-- Table structure for table `transactiontable`
--

CREATE TABLE `transactiontable` (
  `Id` int(11) NOT NULL,
  `Date` datetime DEFAULT NULL,
  `Type` varchar(100) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `Amount` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transactiontable`
--

INSERT INTO `transactiontable` (`Id`, `Date`, `Type`, `Description`, `Amount`) VALUES
(1, '2018-09-07 00:00:00', 'Credit', 'Booking of customermark', 100),
(2, '2018-09-07 00:00:00', 'Debit', 'xyz', 500);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `drivertable`
--
ALTER TABLE `drivertable`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `expensetable`
--
ALTER TABLE `expensetable`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `TId` (`TId`),
  ADD KEY `par_ind` (`TId`);

--
-- Indexes for table `ordertable`
--
ALTER TABLE `ordertable`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `TId` (`TId`),
  ADD KEY `par_Tind` (`TId`);

--
-- Indexes for table `taxitable`
--
ALTER TABLE `taxitable`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `transactiontable`
--
ALTER TABLE `transactiontable`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `drivertable`
--
ALTER TABLE `drivertable`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `expensetable`
--
ALTER TABLE `expensetable`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `ordertable`
--
ALTER TABLE `ordertable`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `taxitable`
--
ALTER TABLE `taxitable`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `transactiontable`
--
ALTER TABLE `transactiontable`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `expensetable`
--
ALTER TABLE `expensetable`
  ADD CONSTRAINT `fk_ETranTable` FOREIGN KEY (`TId`) REFERENCES `transactiontable` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `ordertable`
--
ALTER TABLE `ordertable`
  ADD CONSTRAINT `fk_OTransactionTable` FOREIGN KEY (`TId`) REFERENCES `transactiontable` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
