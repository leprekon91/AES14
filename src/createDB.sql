-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema AES
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema AES
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `AES` DEFAULT CHARACTER SET utf8 ;
USE `AES` ;

-- -----------------------------------------------------
-- Table `users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `users` (
  `user_name` VARCHAR(50) NOT NULL,
  `pass` VARCHAR(50) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `type` INT NOT NULL,
  PRIMARY KEY (`user_name`),
  UNIQUE INDEX `username_UNIQUE` (`user_name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `subjects`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `subjects` (
  `id_subject` INT(2) NOT NULL,
  `subject_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_subject`),
  UNIQUE INDEX `idsubjects_UNIQUE` (`id_subject` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `courses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `courses` (
  `id_course` INT(2) NOT NULL,
  `subjects_id_subjects` INT NOT NULL,
  `course_name` VARCHAR(45) NULL,
  PRIMARY KEY (`id_course`),
  INDEX `fk_courses_subjects_idx` (`subjects_id_subjects` ASC),
  CONSTRAINT `fk_courses_subjects`
    FOREIGN KEY (`subjects_id_subjects`)
    REFERENCES `AES`.`subjects` (`id_subject`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `student_studies_in_course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `student_studies_in_course` (
  `student_name` VARCHAR(50) NOT NULL,
  `courses_id_course` INT(2) NOT NULL,
  PRIMARY KEY (`student_name`, `courses_id_course`),
  INDEX `fk_users_has_courses_courses1_idx` (`courses_id_course` ASC),
  INDEX `fk_users_has_courses_users1_idx` (`student_name` ASC),
  CONSTRAINT `fk_users_has_courses_users1`
    FOREIGN KEY (`student_name`)
    REFERENCES `AES`.`users` (`user_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_courses_courses1`
    FOREIGN KEY (`courses_id_course`)
    REFERENCES `AES`.`courses` (`id_course`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `questions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `questions` (
  `id_question` INT(3) NOT NULL AUTO_INCREMENT,
  `question_text` TEXT(200) NOT NULL,
  `ans_1` TEXT(200) NOT NULL,
  `ans_2` TEXT(200) NOT NULL,
  `ans_3` TEXT(200) NOT NULL,
  `ans_4` TEXT(200) NOT NULL,
  `indicator` INT NULL,
  `subjects_id_subject` INT NOT NULL,
  `teacher_user` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_question`, `subjects_id_subject`),
  INDEX `fk_questions_subjects1_idx` (`subjects_id_subject` ASC),
  INDEX `fk_questions_users1_idx` (`teacher_user` ASC),
  CONSTRAINT `fk_questions_subjects1`
    FOREIGN KEY (`subjects_id_subject`)
    REFERENCES `AES`.`subjects` (`id_subject`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_questions_users1`
    FOREIGN KEY (`teacher_user`)
    REFERENCES `AES`.`users` (`user_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `teachers_teach_subjects`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `teachers_teach_subjects` (
  `teacher_name` VARCHAR(50) NOT NULL,
  `subjects_id_subject` INT NOT NULL,
  PRIMARY KEY (`teacher_name`, `subjects_id_subject`),
  INDEX `fk_users_has_subjects_subjects1_idx` (`subjects_id_subject` ASC),
  INDEX `fk_users_has_subjects_users1_idx` (`teacher_name` ASC),
  CONSTRAINT `fk_users_has_subjects_users1`
    FOREIGN KEY (`teacher_name`)
    REFERENCES `AES`.`users` (`user_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_subjects_subjects1`
    FOREIGN KEY (`subjects_id_subject`)
    REFERENCES `AES`.`subjects` (`id_subject`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `exams`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `exams` (
  `id_exam` INT(2) NOT NULL AUTO_INCREMENT,
  `exam_duration` INT NULL,
  `teacher_instructions` TEXT(200) NULL,
  `student_instructions` TEXT(200) NULL,
  `subjects_id_subject` INT NOT NULL,
  `courses_id_course` INT NOT NULL,
  `users_user_name` VARCHAR(50) NOT NULL,
  `used` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id_exam`, `subjects_id_subject`, `courses_id_course`),
  INDEX `fk_exams_subjects1_idx` (`subjects_id_subject` ASC),
  INDEX `fk_exams_courses1_idx` (`courses_id_course` ASC),
  INDEX `fk_exams_users1_idx` (`users_user_name` ASC),
  CONSTRAINT `fk_exams_subjects1`
    FOREIGN KEY (`subjects_id_subject`)
    REFERENCES `AES`.`subjects` (`id_subject`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_exams_courses1`
    FOREIGN KEY (`courses_id_course`)
    REFERENCES `AES`.`courses` (`id_course`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_exams_users1`
    FOREIGN KEY (`users_user_name`)
    REFERENCES `AES`.`users` (`user_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `exams_has_questions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `exams_has_questions` (
  `exams_id_exam` INT NOT NULL,
  `questions_id_question` INT NOT NULL,
  `exam_courses_id` INT NOT NULL,
  `questions_subjects_id_subject` INT NOT NULL,
  `question_grade` INT NULL,
  PRIMARY KEY (`exams_id_exam`, `questions_id_question`, `exam_courses_id`, `questions_subjects_id_subject`),
  INDEX `fk_exams_has_questions_questions1_idx` (`questions_id_question` ASC, `questions_subjects_id_subject` ASC),
  INDEX `fk_exams_has_questions_exams1_idx` (`exams_id_exam` ASC),
  CONSTRAINT `fk_exams_has_questions_exams1`
    FOREIGN KEY (`exams_id_exam`)
    REFERENCES `AES`.`exams` (`id_exam`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_exams_has_questions_questions1`
    FOREIGN KEY (`questions_id_question` , `questions_subjects_id_subject`)
    REFERENCES `AES`.`questions` (`id_question` , `subjects_id_subject`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `student_answers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `student_answers` (
  `answer` INT NOT NULL,
  `exams_has_questions_exams_id_exam` INT NOT NULL,
  `exams_has_questions_questions_id_question` INT NOT NULL,
  `exams_has_questions_exam_courses_id` INT NOT NULL,
  `exams_has_questions_questions_subjects_id_subject` INT NOT NULL,
  `student_user` VARCHAR(50) NOT NULL,
  INDEX `fk_student_answers_exams_has_questions1_idx` (`exams_has_questions_exams_id_exam` ASC, `exams_has_questions_questions_id_question` ASC, `exams_has_questions_exam_courses_id` ASC, `exams_has_questions_questions_subjects_id_subject` ASC),
  INDEX `fk_student_answers_users1_idx` (`student_user` ASC),
  PRIMARY KEY (`exams_has_questions_exams_id_exam`, `exams_has_questions_questions_id_question`, `exams_has_questions_exam_courses_id`, `exams_has_questions_questions_subjects_id_subject`, `student_user`),
  CONSTRAINT `fk_student_answers_exams_has_questions1`
    FOREIGN KEY (`exams_has_questions_exams_id_exam` , `exams_has_questions_questions_id_question` , `exams_has_questions_exam_courses_id` , `exams_has_questions_questions_subjects_id_subject`)
    REFERENCES `AES`.`exams_has_questions` (`exams_id_exam` , `questions_id_question` , `exam_courses_id` , `questions_subjects_id_subject`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_answers_users1`
    FOREIGN KEY (`student_user`)
    REFERENCES `AES`.`users` (`user_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `exam_solutions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `exam_solutions` (
  `student_user` VARCHAR(50) NOT NULL,
  `exams_id_exam` INT NOT NULL,
  `exams_subjects_id_subject` INT NOT NULL,
  `exams_courses_id_course` INT NOT NULL,
  `grade` INT NULL,
  `approved` TINYINT(1) NOT NULL,
  `teacher_notes` TEXT(200) NULL,
  `teacher_user` VARCHAR(50) NOT NULL,
  `execution_duration` VARCHAR(45) NULL,
  `suspected_of_copying` TINYINT(1) NOT NULL,
  `exam_type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`student_user`, `exams_id_exam`, `exams_subjects_id_subject`, `exams_courses_id_course`),
  INDEX `fk_users_has_exams_exams1_idx` (`exams_id_exam` ASC, `exams_subjects_id_subject` ASC, `exams_courses_id_course` ASC),
  INDEX `fk_users_has_exams_users1_idx` (`student_user` ASC),
  INDEX `fk_student_grades_users1_idx` (`teacher_user` ASC),
  CONSTRAINT `fk_users_has_exams_users1`
    FOREIGN KEY (`student_user`)
    REFERENCES `AES`.`users` (`user_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_exams_exams1`
    FOREIGN KEY (`exams_id_exam` , `exams_subjects_id_subject` , `exams_courses_id_course`)
    REFERENCES `AES`.`exams` (`id_exam` , `subjects_id_subject` , `courses_id_course`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_grades_users1`
    FOREIGN KEY (`teacher_user`)
    REFERENCES `AES`.`users` (`user_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `word_file_solutions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `word_file_solutions` (
  `exam_solutions_student_user` VARCHAR(50) NOT NULL,
  `exam_solutions_exams_id_exam` INT NOT NULL,
  `exam_solutions_exams_subjects_id_subject` INT NOT NULL,
  `exam_solutions_exams_courses_id_course` INT NOT NULL,
  `word_file_add` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`exam_solutions_student_user`, `exam_solutions_exams_id_exam`, `exam_solutions_exams_subjects_id_subject`, `exam_solutions_exams_courses_id_course`),
  CONSTRAINT `fk_word_file_solutions_exam_solutions1`
    FOREIGN KEY (`exam_solutions_student_user` , `exam_solutions_exams_id_exam` , `exam_solutions_exams_subjects_id_subject` , `exam_solutions_exams_courses_id_course`)
    REFERENCES `AES`.`exam_solutions` (`student_user` , `exams_id_exam` , `exams_subjects_id_subject` , `exams_courses_id_course`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;