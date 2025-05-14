-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
SET @@session.restrict_fk_on_non_standard_key=OFF;
SET GLOBAL sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));
-- -----------------------------------------------------
-- Schema lotteon
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema lotteon
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `lotteon` DEFAULT CHARACTER SET utf8 ;
USE `lotteon` ;

-- -----------------------------------------------------
-- Table `lotteon`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`user` (
  `id` VARCHAR(16) NOT NULL,
  `password` CHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `contact` CHAR(13) NOT NULL,
  `zip` CHAR(5) NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  `address_detail` VARCHAR(255) NOT NULL,
  `role` ENUM("member", "seller", "admin", "withdrawed") NOT NULL,
  `register_date` DATE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`member` (
  `user_id` VARCHAR(16) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `gender` ENUM("m", "f") NOT NULL,
  `recent_login_date` DATE NOT NULL,
  `description` TEXT NULL,
  `status` ENUM("normal", "stopped", "dormant", "withdrawed") NULL DEFAULT 'normal',
  `level` ENUM("family", "silver", "gold", "vip", "vvip") NULL DEFAULT 'family',
  `birth_date` DATE NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `fk_member_user1_idx` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_member_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `lotteon`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`seller`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`seller` (
  `business_number` CHAR(12) NOT NULL,
  `user_id` VARCHAR(16) NOT NULL,
  `ceo` VARCHAR(20) NOT NULL,
  `company_name` VARCHAR(50) NOT NULL,
  `seller_number` CHAR(15) NOT NULL,
  `fax` VARCHAR(48) NULL,
  `status` ENUM("run", "stopped", "ready") NOT NULL DEFAULT 'ready',
  PRIMARY KEY (`business_number`, `user_id`),
  INDEX `fk_seller_user1_idx` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `business_number_UNIQUE` (`business_number` ASC) VISIBLE,
  CONSTRAINT `fk_seller_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `lotteon`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`article_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`article_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `subtype_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`notice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`notice` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(50) NOT NULL,
  `content` TEXT NOT NULL,
  `register_date` DATE NULL,
  `type_id` INT NOT NULL,
  `hit` INT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_notice_article_type1_idx` (`type_id` ASC) VISIBLE,
  CONSTRAINT `fk_notice_article_type1`
    FOREIGN KEY (`type_id`)
    REFERENCES `lotteon`.`article_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`product_image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`product_image` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `list_thumbnail_loc` VARCHAR(255) NOT NULL,
  `main_thumbnail_loc` VARCHAR(255) NOT NULL,
  `detail_thumbnail_loc` VARCHAR(255) NOT NULL,
  `detail_image_loc` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`product_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`product_category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `sequence` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `sequence_UNIQUE` (`sequence` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`product_subcategory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`product_subcategory` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `category_id` INT NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `sequence` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_product_subcategory_product_category1_idx` (`category_id` ASC) VISIBLE,
  UNIQUE INDEX `sequence_UNIQUE` (`sequence` ASC) VISIBLE,
  CONSTRAINT `fk_product_subcategory_product_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `lotteon`.`product_category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`product` (
  `id` VARCHAR(45) NOT NULL,
  `category_id` INT NOT NULL,
  `subcategory_id` INT NOT NULL,
  `seller_business_number` CHAR(12) NOT NULL,
  `seller_user_id` VARCHAR(16) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `description` TEXT NOT NULL,
  `price` INT NOT NULL,
  `point` INT NULL DEFAULT 0,
  `discount_rate` TINYINT NULL DEFAULT 0,
  `stock` INT NOT NULL,
  `delivery_fee` INT NULL DEFAULT 0,
  `image_id` INT NOT NULL,
  `status` ENUM("on_sale", "stop_selling") NOT NULL DEFAULT 'on_sale' COMMENT 'status = 상품상태',
  `is_vat_free` ENUM("0", "1") NOT NULL COMMENT 'is_vat_free = 부가세 면세 여부',
  `business_class` VARCHAR(30) NOT NULL COMMENT 'business_class = 사업자구분\n',
  `receipt_issuable` ENUM("0", "1") NOT NULL COMMENT 'receipt_issuable = 영수증 발행 가능 여부(\"0\" = false, \"1\"=true)',
  `origin` VARCHAR(20) NOT NULL COMMENT 'origin = 원산지',
  `quality` ENUM("new", "used") NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_product_product_image_idx` (`image_id` ASC) VISIBLE,
  INDEX `fk_product_product_category1_idx` (`category_id` ASC) VISIBLE,
  INDEX `fk_product_product_subcategory1_idx` (`subcategory_id` ASC) VISIBLE,
  INDEX `fk_product_seller1_idx` (`seller_business_number` ASC, `seller_user_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_product_image`
    FOREIGN KEY (`image_id`)
    REFERENCES `lotteon`.`product_image` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_product_product_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `lotteon`.`product_category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_product_product_subcategory1`
    FOREIGN KEY (`subcategory_id`)
    REFERENCES `lotteon`.`product_subcategory` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_product_seller1`
    FOREIGN KEY (`seller_business_number` , `seller_user_id`)
    REFERENCES `lotteon`.`seller` (`business_number` , `user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`product_options`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`product_options` (
  `id` VARCHAR(45) NOT NULL,
  `product_id` VARCHAR(45) NOT NULL,
  `name` VARCHAR(10) NOT NULL,
  `value` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_product_options_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_options_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `lotteon`.`product` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`order_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`order_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` ENUM("payment_waiting", "paid", "prepare_delivery", "on_delivery", "delivered", "purchase_confirmed", "cancel_requested", "cancelled", "refund_requetsed", "refunded", "exchange_requested", "exchanged") NOT NULL COMMENT 'possible values = [\"payment_waiting\", \"paid\", \"on_delivery\", \"delivered\", \"purchase_confirmed\", \"cancel_requested\", \"canceled\", \"refund_requetsed\", \"refunded\", \"exchange_requested\", \"exchange\"]',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`order` (
  `order_number` VARCHAR(45) NOT NULL,
  `member_id` VARCHAR(16) NOT NULL,
  `payment` VARCHAR(45) NOT NULL,
  `recipient_name` VARCHAR(20) NOT NULL,
  `recipient_contact` VARCHAR(14) NOT NULL,
  `recipient_zip` CHAR(5) NOT NULL,
  `recipient_address` VARCHAR(255) NOT NULL,
  `recipient_address_detail` VARCHAR(255) NOT NULL,
  `description` TEXT NULL,
  `status_id` INT NOT NULL,
  `order_date` DATE NOT NULL,
  PRIMARY KEY (`order_number`),
  INDEX `fk_order_order_status1_idx` (`status_id` ASC) VISIBLE,
  INDEX `fk_order_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_order_status1`
    FOREIGN KEY (`status_id`)
    REFERENCES `lotteon`.`order_status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `lotteon`.`member` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`delivery_company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`delivery_company` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `company_name` VARCHAR(30) NOT NULL COMMENT '\"한진택배\":\"hanjin\", \"롯데택배\":\"lotte\", \"CJ 대한통운\": \"cj\", \"로젠택배\":\"lozen\", \"우체국택배\":\"post_office\"',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`delivery`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`delivery` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `delivery_number` VARCHAR(50) NOT NULL,
  `order_number` VARCHAR(45) NOT NULL,
  `delivery_company_id` INT NULL,
  `receipt_date` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_delivery_delivery_company1_idx` (`delivery_company_id` ASC) VISIBLE,
  INDEX `fk_delivery_order1_idx` (`order_number` ASC) VISIBLE,
  UNIQUE INDEX `delivery_number_UNIQUE` (`delivery_number` ASC) VISIBLE,
  CONSTRAINT `fk_delivery_delivery_company1`
    FOREIGN KEY (`delivery_company_id`)
    REFERENCES `lotteon`.`delivery_company` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_delivery_order1`
    FOREIGN KEY (`order_number`)
    REFERENCES `lotteon`.`order` (`order_number`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`qna`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`qna` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `member_id` VARCHAR(16) NOT NULL,
  `title` VARCHAR(50) NOT NULL,
  `type_id` INT NOT NULL,
  `status` ENUM("open", "close") NULL DEFAULT 'open',
  `content` TEXT NOT NULL,
  `register_date` DATE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_qna_article_type1_idx` (`type_id` ASC) VISIBLE,
  INDEX `fk_qna_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_qna_article_type1`
    FOREIGN KEY (`type_id`)
    REFERENCES `lotteon`.`article_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_qna_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `lotteon`.`member` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`reply`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`reply` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `qna_id` INT NOT NULL,
  `content` TEXT NOT NULL,
  PRIMARY KEY (`id`, `qna_id`),
  INDEX `fk_reply_qna1_idx` (`qna_id` ASC) VISIBLE,
  CONSTRAINT `fk_reply_qna1`
    FOREIGN KEY (`qna_id`)
    REFERENCES `lotteon`.`qna` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`recruit_department`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`recruit_department` (
  `id` INT NOT NULL,
  `name` VARCHAR(10) NOT NULL COMMENT '\"IT\", \"영업\", \"UI/UX\", \"마케팅\", \"재무/회계\", \"법률\"',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`recruit_career`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`recruit_career` (
  `id` INT NOT NULL,
  `career_year` TINYINT NOT NULL COMMENT 'career_year = 연차',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`employment_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`employment_type` (
  `id` INT NOT NULL,
  `type` VARCHAR(10) NOT NULL COMMENT '고용형태(신입, 경력, 인턴 등)',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`recruit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`recruit` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(50) NOT NULL,
  `department_id` INT NOT NULL,
  `career_id` INT NOT NULL,
  `employment_type_id` INT NOT NULL,
  `date_from` DATE NOT NULL,
  `date_to` DATE NOT NULL,
  `content` TEXT NOT NULL,
  `description` ENUM("모집중", "모집완료") NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_recruit_recruit_department1_idx` (`department_id` ASC) VISIBLE,
  INDEX `fk_recruit_recruit_career1_idx` (`career_id` ASC) VISIBLE,
  INDEX `fk_recruit_employment_type1_idx` (`employment_type_id` ASC) VISIBLE,
  CONSTRAINT `fk_recruit_recruit_department1`
    FOREIGN KEY (`department_id`)
    REFERENCES `lotteon`.`recruit_department` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_recruit_recruit_career1`
    FOREIGN KEY (`career_id`)
    REFERENCES `lotteon`.`recruit_career` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_recruit_employment_type1`
    FOREIGN KEY (`employment_type_id`)
    REFERENCES `lotteon`.`employment_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`faq`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`faq` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `type_id` INT NOT NULL,
  `content` TEXT NOT NULL,
  `register_date` DATE NULL,
  `hit` INT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_faq_article_type1_idx` (`type_id` ASC) VISIBLE,
  CONSTRAINT `fk_faq_article_type1`
    FOREIGN KEY (`type_id`)
    REFERENCES `lotteon`.`article_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`point`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`point` (
  `id` INT NOT NULL,
  `member_id` VARCHAR(16) NOT NULL,
  `amount` INT NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `issued_date` DATE NOT NULL,
  `total` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_point_history_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_point_history_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `lotteon`.`member` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`coupon_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`coupon_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL COMMENT '배송비 무료, 개별상품 할인, 생일 할인 등\n',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`coupon_benefit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`coupon_benefit` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `benefit` VARCHAR(45) NOT NULL COMMENT 'possible_values =[\"1000\", \"2000\", \"3000\", \"4000\", \"5000\", \"10\", \"20\", \"30\", \"40\", \"50\", \"free_delivery\"]',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`coupon`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`coupon` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type_id` INT NOT NULL,
  `name` VARCHAR(30) NOT NULL,
  `benefit_id` INT NOT NULL,
  `from` DATE NOT NULL,
  `to` DATE NOT NULL,
  `seller_id` CHAR(12) NOT NULL,
  `issued_amount` INT NOT NULL DEFAULT 0,
  `used_amount` INT NOT NULL DEFAULT 0,
  `status` ENUM("issued", "used") NOT NULL DEFAULT 'issued',
  `description` TEXT NULL,
  `issued_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_coupon_coupon_type1_idx` (`type_id` ASC) VISIBLE,
  INDEX `fk_coupon_coupon_benefit1_idx` (`benefit_id` ASC) VISIBLE,
  CONSTRAINT `fk_coupon_coupon_type1`
    FOREIGN KEY (`type_id`)
    REFERENCES `lotteon`.`coupon_type` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_coupon_coupon_benefit1`
    FOREIGN KEY (`benefit_id`)
    REFERENCES `lotteon`.`coupon_benefit` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`sales`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`sales` (
  `id` INT NOT NULL,
  `order_number` VARCHAR(45) NOT NULL,
  `seller_business_number` CHAR(12) NOT NULL,
  `seller_id` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_sales_order1_idx` (`order_number` ASC) VISIBLE,
  INDEX `fk_sales_seller1_idx` (`seller_business_number` ASC, `seller_id` ASC) VISIBLE,
  CONSTRAINT `fk_sales_order1`
    FOREIGN KEY (`order_number`)
    REFERENCES `lotteon`.`order` (`order_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sales_seller1`
    FOREIGN KEY (`seller_business_number` , `seller_id`)
    REFERENCES `lotteon`.`seller` (`business_number` , `user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`coupon_history`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`coupon_history` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `coupon_id` INT NOT NULL,
  `user_id` VARCHAR(16) NOT NULL,
  `status` ENUM("used", "unused") NULL DEFAULT 'unused',
  `used_date` DATE NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_coupon_history_member1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_coupon_history_coupon1_idx` (`coupon_id` ASC) VISIBLE,
  CONSTRAINT `fk_coupon_history_member1`
    FOREIGN KEY (`user_id`)
    REFERENCES `lotteon`.`member` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_coupon_history_coupon1`
    FOREIGN KEY (`coupon_id`)
    REFERENCES `lotteon`.`coupon` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`cart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`cart` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `member_id` VARCHAR(16) NOT NULL,
  `product_id` VARCHAR(45) NOT NULL,
  `amount` INT NOT NULL DEFAULT 1,
  `register_date` DATE NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_cart_member1_idx` (`member_id` ASC) VISIBLE,
  INDEX `fk_cart_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_cart_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `lotteon`.`member` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_cart_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `lotteon`.`product` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`review_image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`review_image` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `review_id` INT NOT NULL,
  `image_location` VARCHAR(255) NOT NULL,
  `image_location2` VARCHAR(255) NOT NULL,
  `image_location3` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`review` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `product_id` VARCHAR(45) NOT NULL,
  `member_id` VARCHAR(16) NOT NULL,
  `rating` TINYINT NOT NULL,
  `description` TEXT NOT NULL,
  `image_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_review_review_image1_idx` (`image_id` ASC) VISIBLE,
  INDEX `fk_review_member1_idx` (`member_id` ASC) VISIBLE,
  INDEX `fk_review_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_review_review_image1`
    FOREIGN KEY (`image_id`)
    REFERENCES `lotteon`.`review_image` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_review_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `lotteon`.`member` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_review_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `lotteon`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`return_reason`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`return_reason` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `reason` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`return`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`return` (
  `id` INT NOT NULL,
  `order_number` VARCHAR(45) NOT NULL,
  `member_id` VARCHAR(16) NOT NULL,
  `reason_id` INT NOT NULL,
  `description` TEXT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_return_return_reason1_idx` (`reason_id` ASC) VISIBLE,
  INDEX `fk_return_member1_idx` (`member_id` ASC) VISIBLE,
  INDEX `fk_return_order1_idx` (`order_number` ASC) VISIBLE,
  CONSTRAINT `fk_return_return_reason1`
    FOREIGN KEY (`reason_id`)
    REFERENCES `lotteon`.`return_reason` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_return_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `lotteon`.`member` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_return_order1`
    FOREIGN KEY (`order_number`)
    REFERENCES `lotteon`.`order` (`order_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`return_image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`return_image` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `return_id` INT NOT NULL,
  `image_location` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`, `return_id`),
  INDEX `fk_return_image_return1_idx` (`return_id` ASC) VISIBLE,
  CONSTRAINT `fk_return_image_return1`
    FOREIGN KEY (`return_id`)
    REFERENCES `lotteon`.`return` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`terms`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`terms` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `content` TEXT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`video`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`video` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `youtube_url` VARCHAR(255) NULL,
  `thumbnail_url` VARCHAR(255) NULL,
  `title` VARCHAR(255) NULL,
  `description` TEXT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`order_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`order_item` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_number` VARCHAR(45) NOT NULL,
  `product_id` VARCHAR(45) NOT NULL,
  `amount` INT NULL,
  `total_price` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_item_product1_idx` (`product_id` ASC) VISIBLE,
  INDEX `fk_order_item_order1_idx` (`order_number` ASC) VISIBLE,
  CONSTRAINT `fk_order_item_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `lotteon`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_item_order1`
    FOREIGN KEY (`order_number`)
    REFERENCES `lotteon`.`order` (`order_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lotteon`.`order_item_options`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lotteon`.`order_item_options` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_item_id` INT NOT NULL,
  `product_options_id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_item_options_order_item1_idx` (`order_item_id` ASC) VISIBLE,
  INDEX `fk_order_item_options_product_options1_idx` (`product_options_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_item_options_order_item1`
    FOREIGN KEY (`order_item_id`)
    REFERENCES `lotteon`.`order_item` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_item_options_product_options1`
    FOREIGN KEY (`product_options_id`)
    REFERENCES `lotteon`.`product_options` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
