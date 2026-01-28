create database zenoApp; 
use zenoApp;

create table UserAccountInformation(
    UserID INT PRIMARY KEY IDENTITY(1,1),
    Name VARCHAR(50) NOT NULL,
    Username VARCHAR(50) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL
);
 
create table Progress(
    ProgressID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT FOREIGN KEY REFERENCES UserAccountInformation(UserID),
    Percentage INT NOT NULL CHECK (Percentage >= 0),
    LessonsCompleted INT,
    LessonBreakDown INT,
    Streak INT DEFAULT 0
);

create table Assignment(
    AssignmentID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT FOREIGN KEY REFERENCES UserAccountInformation(UserID),
    AssignmentNumber INT NOT NULL,
    Completion VARCHAR(10) NOT NULL CHECK (Completion IN ('YES', 'NO'))
);

create table Incentives(
    IncentiveID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT FOREIGN KEY REFERENCES UserAccountInformation(UserID),
    EarnedToken INT DEFAULT 0
);


create table Leaderboard(
    LeaderboardID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT FOREIGN KEY REFERENCES UserAccountInformation(UserID),
    TotalXP INT DEFAULT 0,
    TokensEarned INT DEFAULT 0
);

