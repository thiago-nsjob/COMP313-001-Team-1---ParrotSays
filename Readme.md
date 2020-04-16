# Parrot Says - Automated Twitter Scrapping

This project aims for leveraging twitter posts in order to create rich and meaninfull event reports within parrot says system. By laveraging aws comprehend service, posts' data are read and analysed in order to extract sentiment, and hazardous condition spotting. 

## Architecture
![Architecture](https://github.com/thiago-nsjob/COMP313-001-Team-1---ParrotSays/blob/dev/assets/project_arch.jpg)
 
## Description
### 1. CloudWatch trigger   
### 2. Twitter Scrapping 
### 3. Message sending to SQS
### 4. SQS triggers analyser lambda
### 5. Post data analysed by AWS Comprehend
### 6. Enriched post data sent to TweetApi
### 7. Api saves tweet on mongo atlas
### 8. Api enables SNS notification
### 9. Admin portal uses TweetApi
### 10. ParrotSays Api integrates MySQL db
### 11. Android App connects to ParrotSays Api 
### 12. Android App receives notification


## License
[MIT](https://choosealicense.com/licenses/mit/)