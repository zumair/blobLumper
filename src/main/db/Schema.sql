

CREATE DATABASE IF NOT EXISTS blobLumper;
USE blobLumper;

CREATE TABLE IF NOT EXISTS TempBucketDetails (
    id integer(25) NOT NULL AUTO_INCREMENT,
    mostSignificantBits  integer(25) NOT NULL,
    leastSignificantBits integer(25) NOT NULL,
    createdDate timestamp NOT NULL,
    
    PRIMARY KEY (id),
    INDEX bitsIDX (mostSignificantBits,leastSignificantBits)
);

CREATE TABLE IF NOT EXISTS DynamicApplicationProperties(
    id integer(25) NOT NULL AUTO_INCREMENT,
    name  varchar(255) NOT NULL,
    value text NOT NULL,
    description varchar(200),
    
    PRIMARY KEY (id),
    INDEX nameDAPIDX (name),
    UNIQUE KEY uni_DYAPPName (name)
    
);

CREATE TABLE IF NOT EXISTS BlobBasePath(
    id integer(25) NOT NULL AUTO_INCREMENT,
    host  varchar(255) NOT NULL,
    basePath varchar(500) NOT NULL,
    folderCount integer(20),
    active  boolean default 0,
    
    PRIMARY KEY (id),
    INDEX hstBBPIDX (host),
    INDEX bseBBPPIDX (basePath),
    UNIQUE KEY uniHostBasePath (host, basePath)
    
);


CREATE TABLE IF NOT EXISTS BlobObject(
    id integer(25) NOT NULL AUTO_INCREMENT,
    basePathId  integer(25) NOT NULL,
    subFilePath varchar(500),
    fileName    varchar(255),
    fileExtension varchar(10),
    
    PRIMARY KEY (id),
    CONSTRAINT fk_bBPId FOREIGN KEY (basePathId) REFERENCES BlobBasePath (id),
    INDEX fileNameBLIDX (fileName),
    INDEX fileExtBLEIDX (fileName)
    
);



