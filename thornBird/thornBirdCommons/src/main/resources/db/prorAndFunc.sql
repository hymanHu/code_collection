-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: maindb
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping routines for database 'maindb'
--
/*!50003 DROP PROCEDURE IF EXISTS `spInitCountriesAndCities` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`hymanHu`@`%` PROCEDURE `spInitCountriesAndCities`()
BEGIN
	-- 定义变量
    declare countryName_ varchar(50);
    declare countryCode_ varchar(20);
    declare countryCode2_ varchar(20);
    declare continent_ varchar(20);
    declare capital_ int(11) default 0;
    declare cityId_ int(11) default 0;
    declare cityName_ varchar(50);
    declare countryId_ int(11) default 0;
    declare population_ int(11) default 0;
    declare region_ varchar(50);
    declare surfaceArea_ float(10,2);
    declare indepYear_ int;
    declare countryPopulation_ int;
    declare lifeExpectancy_ float(3,1);
    declare gnp_ float(10,1);
    declare countryLocalName_ varchar(50);
    declare governmentForm_ varchar(50);
    declare headOfState_ varchar(60);
    declare district_ varchar(60);
    
    declare errorCode CHAR(5) DEFAULT '00000';
    declare msg text;
    
    -- 定义事务异常监控标志、游标执行标志
    declare t_error integer default 0;
    declare done int default false;
    
    -- 定义两个游标
    declare myCursor_1 cursor for (
		select name, code, code2, continent, capital, Region, SurfaceArea, IndepYear, Population, 
        LifeExpectancy, GNP, LocalName, GovernmentForm, HeadOfState 
        from world.country);
	declare myCursor_2 cursor for (select id, name, countryCode, population, District from world.city);
    
    -- 设置事务、游标开关
    declare continue handler for not found set done = true;
    declare continue handler for sqlexception
    begin
		-- 获取异常code,异常信息
		GET DIAGNOSTICS CONDITION 1 errorCode = RETURNED_SQLSTATE, msg = MESSAGE_TEXT;
		set t_error = 1;
	end;
    
    -- 开启事务
    start transaction;
    
    -- 循环游标，处理业务
    -- copy countries information
    open myCursor_1;
		fetch next from myCursor_1 into 
			countryName_, countryCode_, countryCode2_, continent_, capital_, region_, surfaceArea_, indepYear_, 
            countryPopulation_, lifeExpectancy_, gnp_, countryLocalName_, governmentForm_, headOfState_;
        while not done do
			if exists(select country_id from maindb.m_country where country_code = countryCode_) then
				update maindb.m_country set 
					country_name = countryName_, country_code = countryCode_, 
					country_code2 = countryCode2_, continent = continent_, capital = capital_, 
                    region = region_, surface_area = surfaceArea_, indep_year = indepYear_, 
                    population = countryPopulation_, life_expectancy = lifeExpectancy_, 
                    gnp = gnp_, government_form = governmentForm_, head_of_state = headOfState_,
					local_country_name = countryLocalName_, language_id = 0, currency_id = 0, 
					date_modified = DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'), 
					date_created = DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s') 
				where country_code = countryCode_;
            else
				insert into maindb.m_country(
					country_name, country_code, country_code2, continent, capital, region, 
                    surface_area, indep_year, population, life_expectancy, gnp, government_form, 
                    head_of_state, local_country_name, date_modified, date_created) 
				values(countryName_, countryCode_, countryCode2_, continent_, capital_, region_, 
					surfaceArea_, indepYear_, countryPopulation_, lifeExpectancy_, gnp_,  
                    governmentForm_, headOfState_, countryLocalName_, 
					DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'), DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'));
            end if;	
            
			fetch next from myCursor_1 into 
				countryName_, countryCode_, countryCode2_, continent_, capital_, region_, surfaceArea_, indepYear_, 
				countryPopulation_, lifeExpectancy_, gnp_, countryLocalName_, governmentForm_, headOfState_;
        end while;
    close myCursor_1;
    
    -- 两个游标，需要重置 done
    set done = 0;

    -- copy cities information
    open myCursor_2;
		fetch next from myCursor_2 into cityId_, cityName_, countryCode_, population_, district_;
		while not done do
			if exists(select city_id from maindb.m_city where city_id = cityId_) then
				update maindb.m_city set 
					city_name = cityName_, 
					country_id = (select country_id from maindb.m_country where country_code = countryCode_), 
					population = population_, district = district_, 
					date_modified = DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'), 
					date_created = DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s') 
				where city_id = cityId_;
			else
				insert into maindb.m_city(
					city_id, city_name, country_id, population, district, date_modified, date_created) 
				values(
					cityId_, cityName_, 
					(select country_id from maindb.m_country where country_code = countryCode_), 
                    population_, district_, 
					DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'), DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'));
			end if;	
			
			fetch next from myCursor_2 into cityId_, cityName_, countryCode_, population_, district_;
		end while;
	close myCursor_2;
    
	-- 提交事务
	if t_error = 1 then
		select errorCode, msg;
		rollback;
	else
		commit;
	end if;
    -- 返回标识位的结果集
    select t_error;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `spInitCountryAndCity` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`hymanHu`@`%` PROCEDURE `spInitCountryAndCity`()
BEGIN
    -- 定义变量
    declare countryName_ varchar(50);
    declare countryCode_ varchar(20);
    declare countryCode2_ varchar(20);
    declare continent_ varchar(20);
    declare capital_ int(11) default 0;
    declare cityId_ int(11) default 0;
    declare cityName_ varchar(50);
    declare countryId_ int(11) default 0;
    declare population_ int(11) default 0;
    
    declare errorCode CHAR(5) DEFAULT '00000';
    declare msg text;
    
    -- 定义事务异常监控标志、游标执行标志
    declare t_error integer default 0;
    declare done int default false;
    
    -- 定义两个游标
    declare myCursor_1 cursor for (select name, code, code2, continent, capital from world.country);
	declare myCursor_2 cursor for (select id, name, countryCode, population from world.city);
    
    -- 设置事务、游标开关
    declare continue handler for not found set done = true;
    declare continue handler for sqlexception
	begin
		-- 获取异常code,异常信息
		GET DIAGNOSTICS CONDITION 1 errorCode = RETURNED_SQLSTATE, msg = MESSAGE_TEXT;
		set t_error = 1;
	end;
    
    -- 开启事务
    start transaction;
    
    -- 循环游标，处理业务
    -- copy countries information
    open myCursor_1;
		fetch next from myCursor_1 into countryName_, countryCode_, countryCode2_, continent_, capital_;
        while not done do
			if exists(select countryId from maindb.country where countryCode = countryCode_) then
				update maindb.country set 
					countryName = countryName_, countryCode = countryCode_, 
					countryCode2 = countryCode2_, continent = continent_, capital = capital_, 
					languageId = 0, currencyId = 0, 
					dateModified = DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'), 
					dateCreated = DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s') 
				where countryCode = countryCode_;
            else
				insert into maindb.country(
					countryName, countryCode, countryCode2, continent, capital, dateModified, dateCreated) 
				values(countryName_, countryCode_, countryCode2_, continent_, capital_, 
					DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'), DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'));
            end if;	
            
			fetch next from myCursor_1 into countryName_, countryCode_, countryCode2_, continent_, capital_;
        end while;
    close myCursor_1;
    
    -- 两个游标，需要重置 done
    set done = 0;

    -- copy cities information
    open myCursor_2;
		fetch next from myCursor_2 into cityId_, cityName_, countryCode_, population_;
		while not done do
			if exists(select cityId from maindb.city where cityId = cityId_) then
				update maindb.city set 
					cityName = cityName_, 
					countryId = (select countryId from maindb.country where countryCode = countryCode_), 
					population = population_, 
					dateModified = DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'), 
					dateCreated = DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s') 
				where cityId = cityId_;
			else
				insert into maindb.city(
					cityId, cityName, countryId, population, dateModified, dateCreated) 
				values(
					cityId_, cityName_, 
					(select countryId from maindb.country where countryCode = countryCode_), 
                    population_,  
					DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'), DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'));
			end if;	
			
			fetch next from myCursor_2 into cityId_, cityName_, countryCode_, population_;
		end while;
	close myCursor_2;
    
	-- 提交事务
	if t_error = 1 then
		select errorCode, msg;
		rollback;
	else
		commit;
	end if;
    -- 返回标识位的结果集
    select t_error;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-08-28 13:29:14
