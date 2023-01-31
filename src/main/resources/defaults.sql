CREATE TABLE IF NOT EXISTS `players`
(
    player_id     INT             AUTO_INCREMENT,
    uuid          CHAR(36)        NOT NULL,
    username      VARCHAR(17)     NOT NULL,
    PRIMARY KEY (player_id)
);