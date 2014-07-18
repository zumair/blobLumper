USE blobBucket;
#----------------------
#Application Properties
#----------------------

INSERT INTO DynamicApplicationProperties(name,value,description)
VALUES ('emptyBucket.temp.base.path','/Users/zohaibumairMacMini/Documents/temp',
'Base directory for temporary buckets' );

commit;