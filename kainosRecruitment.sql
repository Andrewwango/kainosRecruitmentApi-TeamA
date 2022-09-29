
USE team_A; -- team_A 


 -- creating tables and relationships--
 
CREATE TABLE band(bandID SMALLINT PRIMARY KEY,
bandName VARCHAR(50));


CREATE TABLE capabilities(capabilityID SMALLINT PRIMARY KEY,
capabilityName VARCHAR(50));


CREATE TABLE jobRoles(jobRoleID MEDIUMINT PRIMARY KEY AUTO_INCREMENT,
roleName VARCHAR(50),
specification VARCHAR(500),
link VARCHAR(500),
bandID SMALLINT,
capabilityID SMALLINT,
FOREIGN KEY (bandID) REFERENCES band(bandID),
FOREIGN KEY (capabilityID) REFERENCES capabilities(capabilityID));


CREATE TABLE competencies(competenciesID SMALLINT PRIMARY KEY,
description VARCHAR(500),
category VARCHAR(80),
bandID SMALLINT,
FOREIGN KEY (bandID) REFERENCES band(bandID));

CREATE TABLE trainings(trainingID MEDIUMINT PRIMARY KEY AUTO_INCREMENT,
trainingName VARCHAR(100) UNIQUE,
trainingDate DATE,
durationHours DECIMAL(2,1),
registration CHAR(30));


CREATE TABLE bandTrainings(bandID SMALLINT,
trainingName VARCHAR(100),
FOREIGN KEY (bandID) REFERENCES band(bandID),
FOREIGN KEY (trainingName) REFERENCES trainings(trainingName));


-- inserting values into tables -- 
INSERT INTO band
VALUES
(1, "principal"),
(2, "manager"),
(3, "consultant"),
(4, "senior associate"),
(5, "associate"),
(6, "trainee");


INSERT INTO capabilities(capabilityID, capabilityName)
VALUES 
(1, "Data & AI"),
(2,"Engineering");


INSERT INTO jobRoles(roleName, specification, link, bandID, capabilityID)
VALUES
("Data Analyst","As a Data Analyst (Associate) in Kainos, you’ll be responsible for matching the needs of data insight with understanding of the available data. Data analysts work closely with customers to produce insight products including reports, dashboards and visualisations but also contribute to project understanding of existing data structures so that inputs and outputs are fully understood.","https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?OR=Teams%2DHL&CT=1664288844676&clickparams=eyJBcHBOYW1lIjoiVGVhbXMtRGVza3RvcCIsIkFwcFZlcnNpb24iOiIyOC8yMjA3MzEwMTAwNyIsIkhhc0ZlZGVyYXRlZFVzZXIiOmZhbHNlfQ%3D%3D&id=%2Fpeople%2FJob%20Specifications%2FData%2FJob%20profile%20%2D%20Data%20Analyst%20%28As%29%2Epdf&viewid=4ad66b2c%2D695f%2D4d2b%2D9622%2D07ad809bd9a9&parent=%2Fpeople%2FJob%20Specifications%2FData",5, 1),
("Data Architect","As a Data Architect (Manager) in Kainos, you’ll be responsible for providing SME guidance in traditional data architecture disciplines around data structures, data flows, data sourcing and data governance. Data Architects work closely with clients to understand their data requirements and take responsibility for ensuring solutions are fit for purpose.","https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?OR=Teams%2DHL&CT=1664288844676&clickparams=eyJBcHBOYW1lIjoiVGVhbXMtRGVza3RvcCIsIkFwcFZlcnNpb24iOiIyOC8yMjA3MzEwMTAwNyIsIkhhc0ZlZGVyYXRlZFVzZXIiOmZhbHNlfQ%3D%3D&id=%2Fpeople%2FJob%20Specifications%2FData%2FJob%20profile%20%2D%20Data%20Architect%20%28M%29%2Epdf&viewid=4ad66b2c%2D695f%2D4d2b%2D9622%2D07ad809bd9a9&parent=%2Fpeople%2FJob%20Specifications%2FData",2, 1),
("Data Engineer","As a Data Engineer (Associate) in Kainos you will work within a multi-skilled agile team to design and develop large-scale data processing software to meet user needs in demanding production environments.","https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?OR=Teams%2DHL&CT=1664288844676&clickparams=eyJBcHBOYW1lIjoiVGVhbXMtRGVza3RvcCIsIkFwcFZlcnNpb24iOiIyOC8yMjA3MzEwMTAwNyIsIkhhc0ZlZGVyYXRlZFVzZXIiOmZhbHNlfQ%3D%3D&id=%2Fpeople%2FJob%20Specifications%2FData%2FJob%20profile%20%2D%20Data%20Engineer%20%28As%29%2Epdf&viewid=4ad66b2c%2D695f%2D4d2b%2D9622%2D07ad809bd9a9&parent=%2Fpeople%2FJob%20Specifications%2FData",5, 1),
("Data Solution Architect","As a Data Solution Architect (Manager) in Kainos, you’ll be responsible for a multi- skilled agile teams to design and deliver contemporary data solutions. You will be a quality orientated pragmatist, where you balance trade-offs to successfully deliver complex solutions. You will be viewed as an authority figure for data technology solutions, providing strong technical and thought leadership.","https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?OR=Teams%2DHL&CT=1664288844676&clickparams=eyJBcHBOYW1lIjoiVGVhbXMtRGVza3RvcCIsIkFwcFZlcnNpb24iOiIyOC8yMjA3MzEwMTAwNyIsIkhhc0ZlZGVyYXRlZFVzZXIiOmZhbHNlfQ%3D%3D&id=%2Fpeople%2FJob%20Specifications%2FData%2FJob%20profile%20%2D%20Data%20Solution%20Architect%20%28M%29%2Epdf&viewid=4ad66b2c%2D695f%2D4d2b%2D9622%2D07ad809bd9a9&parent=%2Fpeople%2FJob%20Specifications%2FData",2, 1),
("Lead Data Engineer","As a Data Engineer (Associate) in Kainos you will work within a multi-skilled agile team to design and develop large-scale data processing software to meet user needs in demanding production environments.","https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?OR=Teams%2DHL&CT=1664288844676&clickparams=eyJBcHBOYW1lIjoiVGVhbXMtRGVza3RvcCIsIkFwcFZlcnNpb24iOiIyOC8yMjA3MzEwMTAwNyIsIkhhc0ZlZGVyYXRlZFVzZXIiOmZhbHNlfQ%3D%3D&id=%2Fpeople%2FJob%20Specifications%2FData%2FJob%20profile%20%2D%20Data%20Engineer%20%28As%29%2Epdf&viewid=4ad66b2c%2D695f%2D4d2b%2D9622%2D07ad809bd9a9&parent=%2Fpeople%2FJob%20Specifications%2FData",3, 1),
("Lead Software Engineer","As a Lead Software Engineer (Consultant) in Kainos, you’ll be responsible for leading teams and developing high quality solutions which delight our customers and impact the lives of users worldwide. It’s a fast-paced environment so it is important for you to make sound, reasoned decisions.","https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?OR=Teams%2DHL&CT=1664288844676&clickparams=eyJBcHBOYW1lIjoiVGVhbXMtRGVza3RvcCIsIkFwcFZlcnNpb24iOiIyOC8yMjA3MzEwMTAwNyIsIkhhc0ZlZGVyYXRlZFVzZXIiOmZhbHNlfQ%3D%3D&id=%2Fpeople%2FJob%20Specifications%2FEngineering%2FJob%20profile%20%2D%20Lead%20Software%20Engineer%20%28Consultant%29%2Epdf&viewid=4ad66b2c%2D695f%2D4d2b%2D9622%2D07ad809bd9a9&parent=%2Fpeople%2FJob%20Specifications%2FEngineering",3, 2),
("Lead Test Engineer","As a Lead Test Engineer (Consultant) in Kainos, you’ll be a technical leader and innovator in software testing, providing strong test leadership and direction within a multi-skilled agile team. Taking responsibility for the strategy, design and development of automated, manual, and non-functional tests, you’ll help the team to deliver working application software that meets user needs and is of sufficient quality for promotion to users.","https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?OR=Teams%2DHL&CT=1664288844676&clickparams=eyJBcHBOYW1lIjoiVGVhbXMtRGVza3RvcCIsIkFwcFZlcnNpb24iOiIyOC8yMjA3MzEwMTAwNyIsIkhhc0ZlZGVyYXRlZFVzZXIiOmZhbHNlfQ%3D%3D&id=%2Fpeople%2FJob%20Specifications%2FTesting%2FJob%20profile%20%2D%20Lead%20Test%20Engineer%20%28Consultant%29%2Epdf&viewid=4ad66b2c%2D695f%2D4d2b%2D9622%2D07ad809bd9a9&parent=%2Fpeople%2FJob%20Specifications%2FTesting",3, 2),
("Principle Architect","As a Principal Architect (Principal) in Kainos, you’ll be accountable for successful delivery of large-scale high-quality solutions which delight our customers and impact the lives of users worldwide.","https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?OR=Teams%2DHL&CT=1664288844676&clickparams=eyJBcHBOYW1lIjoiVGVhbXMtRGVza3RvcCIsIkFwcFZlcnNpb24iOiIyOC8yMjA3MzEwMTAwNyIsIkhhc0ZlZGVyYXRlZFVzZXIiOmZhbHNlfQ%3D%3D&id=%2Fpeople%2FJob%20Specifications%2FEngineering%2FJob%20Profile%20%2D%20Principal%20Architect%20%28Principal%29%2Epdf&viewid=4ad66b2c%2D695f%2D4d2b%2D9622%2D07ad809bd9a9&parent=%2Fpeople%2FJob%20Specifications",1, 2),
("Principle Data Architect","As a Principal Data Architect in Kainos, you’ll be accountable for successful delivery of data solutions across multiple customers; taking responsibility across traditional data architecture disciplines around data structures, data flows, data sourcing and data governance.  This will mean working at a solution and enterprise level.","https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?OR=Teams%2DHL&CT=1664288844676&clickparams=eyJBcHBOYW1lIjoiVGVhbXMtRGVza3RvcCIsIkFwcFZlcnNpb24iOiIyOC8yMjA3MzEwMTAwNyIsIkhhc0ZlZGVyYXRlZFVzZXIiOmZhbHNlfQ%3D%3D&id=%2Fpeople%2FJob%20Specifications%2FData%2FJob%20profile%20%2D%20Principal%20Data%20Architect%20%28P%29%2Epdf&viewid=4ad66b2c%2D695f%2D4d2b%2D9622%2D07ad809bd9a9&parent=%2Fpeople%2FJob%20Specifications",1, 1),
("Principle Data Solution Architect","As a Principal Data Solution Architect in Kainos, you’ll be accountable for successful delivery of contemporary data solutions across multiple customers.","https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?OR=Teams%2DHL&CT=1664288844676&clickparams=eyJBcHBOYW1lIjoiVGVhbXMtRGVza3RvcCIsIkFwcFZlcnNpb24iOiIyOC8yMjA3MzEwMTAwNyIsIkhhc0ZlZGVyYXRlZFVzZXIiOmZhbHNlfQ%3D%3D&id=%2Fpeople%2FJob%20Specifications%2FData%2FJob%20profile%20%2D%20Principal%20Data%20Solution%20Architect%20%28P%29%2Epdf&viewid=4ad66b2c%2D695f%2D4d2b%2D9622%2D07ad809bd9a9&parent=%2Fpeople%2FJob%20Specifications",1, 1),
("Principle Test Architect","As a Principal Test Architect (Principal) in Kainos, you’ll be responsible for ensuring we deliver high quality solutions which delight our customers and impact the lives of users worldwide.","https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?OR=Teams%2DHL&CT=1664288844676&clickparams=eyJBcHBOYW1lIjoiVGVhbXMtRGVza3RvcCIsIkFwcFZlcnNpb24iOiIyOC8yMjA3MzEwMTAwNyIsIkhhc0ZlZGVyYXRlZFVzZXIiOmZhbHNlfQ%3D%3D&id=%2Fpeople%2FJob%20Specifications%2FEngineering%2FJob%20profile%20%2D%20Principal%20Test%20Architect%20%28P%29%2Epdf&viewid=4ad66b2c%2D695f%2D4d2b%2D9622%2D07ad809bd9a9&parent=%2Fpeople%2FJob%20Specifications",1, 2),
("Senior Data Engineer","As a Senior Data Engineer (Senior Associate) at Kainos, you will be responsible or designing and developing data processing and data persistence software components for solutions which handle data at scale. Working in agile teams, Senior Data Engineers provide strong development leadership and take responsibility for significant technical components of data systems .","https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?OR=Teams%2DHL&CT=1664288844676&clickparams=eyJBcHBOYW1lIjoiVGVhbXMtRGVza3RvcCIsIkFwcFZlcnNpb24iOiIyOC8yMjA3MzEwMTAwNyIsIkhhc0ZlZGVyYXRlZFVzZXIiOmZhbHNlfQ%3D%3D&id=%2Fpeople%2FJob%20Specifications%2FData%2FJob%20profile%20%2D%20Senior%20Data%20Engineer%20%28SA%29%2Epdf&viewid=4ad66b2c%2D695f%2D4d2b%2D9622%2D07ad809bd9a9&parent=%2Fpeople%2FJob%20Specifications",4, 1),
("Senior Software Engineer","As a Senior Software Engineer (Senior Associate) in Kainos, you’ll be responsible for developing high quality solutions which delight our customers and impact the lives of users worldwide.","https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?OR=Teams%2DHL&CT=1664288844676&clickparams=eyJBcHBOYW1lIjoiVGVhbXMtRGVza3RvcCIsIkFwcFZlcnNpb24iOiIyOC8yMjA3MzEwMTAwNyIsIkhhc0ZlZGVyYXRlZFVzZXIiOmZhbHNlfQ%3D%3D&id=%2Fpeople%2FJob%20Specifications%2FEngineering%2FJob%20profile%20%2D%20Senior%20Software%20Engieneer%20%28Senior%20Associate%29%2Epdf&viewid=4ad66b2c%2D695f%2D4d2b%2D9622%2D07ad809bd9a9&parent=%2Fpeople%2FJob%20Specifications",4, 2);

INSERT INTO jobRoles(roleName, specification, link, bandID, capabilityID)
VALUES
("Senior Test Engineer","As a Senior Test Engineer (Senior Associate) in Kainos, you’ll work within a multi-skilled agile team, developing and executing functional automated and manual tests to help the team deliver working application software that meets user needs. ","https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?OR=Teams%2DHL&CT=1664288844676&clickparams=eyJBcHBOYW1lIjoiVGVhbXMtRGVza3RvcCIsIkFwcFZlcnNpb24iOiIyOC8yMjA3MzEwMTAwNyIsIkhhc0ZlZGVyYXRlZFVzZXIiOmZhbHNlfQ%3D%3D&id=%2Fpeople%2FJob%20Specifications%2FTesting%2FJob%20profile%20%2D%20Senior%20Test%20Engineer%20%28Senior%20Associate%29%2Epdf&viewid=4ad66b2c%2D695f%2D4d2b%2D9622%2D07ad809bd9a9&parent=%2Fpeople%2FJob%20Specifications",4, 2),
("Software Engineer","As a Software Engineer (Associate) in Kainos, you’ll be responsible for developing high quality solutions which delight our customers and impact the lives of users worldwide. ","https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?OR=Teams%2DHL&CT=1664288844676&clickparams=eyJBcHBOYW1lIjoiVGVhbXMtRGVza3RvcCIsIkFwcFZlcnNpb24iOiIyOC8yMjA3MzEwMTAwNyIsIkhhc0ZlZGVyYXRlZFVzZXIiOmZhbHNlfQ%3D%3D&id=%2Fpeople%2FJob%20Specifications%2FEngineering%2FJob%20profile%20%2D%20Software%20Engineer%20%28Associate%29%2Epdf&viewid=4ad66b2c%2D695f%2D4d2b%2D9622%2D07ad809bd9a9&parent=%2Fpeople%2FJob%20Specifications%2FEngineering",5, 2),
("Solution Architect","As a Solution Architect (Manager) in Kainos, you’ll be responsible for leading multi-skilled agile teams to design and deliver high quality solutions which delight our customers and impact the lives of users worldwide.","https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?OR=Teams%2DHL&CT=1664288844676&clickparams=eyJBcHBOYW1lIjoiVGVhbXMtRGVza3RvcCIsIkFwcFZlcnNpb24iOiIyOC8yMjA3MzEwMTAwNyIsIkhhc0ZlZGVyYXRlZFVzZXIiOmZhbHNlfQ%3D%3D&id=%2Fpeople%2FJob%20Specifications%2FEngineering%2FJob%20Profile%20%2D%20Solution%20Architect%20%28Manager%29%2Epdf&viewid=4ad66b2c%2D695f%2D4d2b%2D9622%2D07ad809bd9a9&parent=%2Fpeople%2FJob%20Specifications%2FEngineering",2, 2),
("Test Architect", "As a Test Architect (Manager) in Kainos, you’ll be responsible for ensuring we deliver high quality solutions which delight our customers and impact the lives of users worldwide.", "https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?OR=Teams%2DHL&CT=1664288844676&clickparams=eyJBcHBOYW1lIjoiVGVhbXMtRGVza3RvcCIsIkFwcFZlcnNpb24iOiIyOC8yMjA3MzEwMTAwNyIsIkhhc0ZlZGVyYXRlZFVzZXIiOmZhbHNlfQ%3D%3D&id=%2Fpeople%2FJob%20Specifications%2FTesting%2FJob%20profile%20%2D%20Test%20Architect%20%28Manager%29%2Epdf&viewid=4ad66b2c%2D695f%2D4d2b%2D9622%2D07ad809bd9a9&parent=%2Fpeople%2FJob%20Specifications",2, 2),
("Test Engineer","As a Test Engineer (Associate) in Kainos, you’ll work within a multi-skilled agile team, developing and executing functional automated and manual tests to help the team deliver working application software that meets user needs. ","https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?OR=Teams%2DHL&CT=1664288844676&clickparams=eyJBcHBOYW1lIjoiVGVhbXMtRGVza3RvcCIsIkFwcFZlcnNpb24iOiIyOC8yMjA3MzEwMTAwNyIsIkhhc0ZlZGVyYXRlZFVzZXIiOmZhbHNlfQ%3D%3D&id=%2Fpeople%2FJob%20Specifications%2FTesting%2FJob%20profile%20%2D%20Test%20Engineer%20%28Associate%29%2Epdf&viewid=4ad66b2c%2D695f%2D4d2b%2D9622%2D07ad809bd9a9&parent=%2Fpeople%2FJob%20Specifications",5, 2),
("Trainee Data Engineer","As a Trainee Data Engineer (Trainee) in Kainos you will enter the Kainos Big Data Academy. Successful graduates of the Big Data Academy will become Associate Data Engineers, following a period of extended project-based training.  ","https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?id=%2Fpeople%2FJob%20Specifications%2FData%2FJob%20profile%20%2D%20Trainee%20Data%20Engineer%20%28T%29%2Epdf&parent=%2Fpeople%2FJob%20Specifications",6, 1),
("Trainee Test Engineer","As a Test Engineer (Trainee) in Kainos, you’ll work within a multi-skilled agile team, developing and executing functional automated and manual tests to help the team deliver working application software that meets user needs. ","https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?id=%2Fpeople%2FJob%20Specifications%2FTesting%2FJob%20profile%20%2D%20Trainee%20Test%20Engineer%20%28Trainee%29%2Epdf&parent=%2Fpeople%2FJob%20Specifications",6, 2);


INSERT INTO competencies(competenciesID, description, category, bandID)
VALUES 
(1, "Encouragesinvolvement fromothers to deliver through collaboration better resultsfor Kainos. Recognisesand buildson individual strengths of colleagues and team memberswhile building relationships based on trust. Consistently publicise what the team members have achieved and give feedback and recognition awards where appropriate.", "Working within teams", 3),
(2, "Encouragescollaborative team working within immediateteamsand across the wholebusiness. Supportsan environment where others can make mistakes and learn from it.Open to giving and receiving honest feedback in order to highlight areas of improvement and recognise high performance.", "Working within teams", 4),
(3, "Actively participatesand cooperatesto support others within the team to achieve common goals.Able to interact effectively in stressful or frustrating situations, 
knowing when to step away for composure.", "Working within teams", 5),
(4, "Working within teams", "Aware of the consequences of own behaviour and how this may affect others within the team. 
Supportsthe sharing of knowledge, information and learning with other colleagues.", 6),
(5, "Sets an example of leading internal and external meetings through preparation, prioritisation, and considered agendas,
ensuring any challenges and issues are discussed.", "Effective meetings", 3),
(6, "Consistently prepared for meetings and effectively managesown diary for preparation and an agenda is set in advance. A
ctively seeks inputfrom colleagues and challenges others where appropriate.", "Effective meetings", 4),
(7, "Establishes effective meetings through setting an agenda and coming prepared and speaking on projects calls where appropriate. Follows up and delivers upon actions from meetings.", "Effective meetings", 5),
(8, "Effectively manages diary, coming prepared for meetings and actively listen.", "Effective meetings", 6);



INSERT INTO trainings (trainingName, trainingDate, durationHours, registration)
VALUES
("Enhancing your Communication Skills", "2022-10-17", 3,"Please complete a JIRA request" ),
("Step for Success", "2022-10-12", 3,"Please complete a JIRA request" ),
("Having Courageous Conversations", "2022-11-17", 3,"Please complete a JIRA request" ),
("Conducting Effective Meetings", "2022-10-04" , 2.5,"Please complete a JIRA request" );


INSERT INTO bandTrainings (bandID, trainingName)
VALUES
(6, "Enhancing your Communication Skills"),
(5, "Enhancing your Communication Skills"),
(4, "Enhancing your Communication Skills"),
(6, "Step for Success"),
(5, "Step for Success"),
(4, "Step for Success"),
(4, "Having Courageous Conversations"),
(3, "Having Courageous Conversations"),
(2, "Having Courageous Conversations"),
(1, "Having Courageous Conversations"),
(6, "Conducting Effective Meetings"),
(5, "Conducting Effective Meetings"),
(4, "Conducting Effective Meetings");



