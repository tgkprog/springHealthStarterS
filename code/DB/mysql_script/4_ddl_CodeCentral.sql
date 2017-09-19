USE `health2`; 


SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


DROP TABLE IF EXISTS `ActivityDailyDetail`;
CREATE TABLE `ActivityDailyDetail` (
  `id` int(11) NOT NULL,
  `authorId` varchar(100) NOT NULL,
  `date` date NOT NULL,
  `activityType` int(11) NOT NULL,
  `narration` varchar(2000) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--

DROP TABLE IF EXISTS `ActiviyDailyMain`;
CREATE TABLE `ActiviyDailyMain` (
  `id` int(11) NOT NULL,
  `authorId` varchar(100) NOT NULL,
  `date` date NOT NULL,
  `count` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
ALTER TABLE `ActivityDailyDetail`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `ActiviyDailyMain`
--
ALTER TABLE `ActiviyDailyMain`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ActivityDailyDetail`
--
ALTER TABLE `ActivityDailyDetail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `ActiviyDailyMain`
--
ALTER TABLE `ActiviyDailyMain`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;SET FOREIGN_KEY_CHECKS=1;

