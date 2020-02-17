## Script to create the Parrot Says user MySQL Admin.
## This user is only able to make change on parrotsays datababase.
CREATE USER 'psadmin'@'localhost' IDENTIFIED BY '11qq@@WW';
GRANT ALL PRIVILEGES ON parrotsays.* TO 'psadmin'@'localhost';
FLUSH PRIVILEGES;