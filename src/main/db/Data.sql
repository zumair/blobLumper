USE blobLumper;
#----------------------
#Application Properties
#----------------------

INSERT INTO DynamicApplicationProperties(name,value,description)
VALUES ('emptyBucket.temp.base.path','/Users/zohaibumairMacMini/Documents/temp',
'Base directory for temporary buckets' )
ON DUPLICATE KEY UPDATE name = name;


INSERT INTO BlobBasePath(host,basePath,active)
VALUES ('localhost','/Users/zohaibumairMacMini/Documents/temp1',true )
ON DUPLICATE KEY UPDATE host = host;


INSERT INTO BlobBasePath(host,basePath,active)
VALUES ('localhost','/Users/zohaibumairMacMini/Documents/temp2',true )
ON DUPLICATE KEY UPDATE host = host;


INSERT INTO BlobBasePath(host,basePath,active)
VALUES ('localhost','/Users/zohaibumairMacMini/Documents/temp3',true )
ON DUPLICATE KEY UPDATE host = host;

commit;