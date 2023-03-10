CREATE TABLE IF NOT EXISTS hits
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    app        VARCHAR(300)                            NOT NULL,
    uri        VARCHAR(300)                            NOT NULL,
    ip         VARCHAR(300)                            NOT NULL,
    created_on TIMESTAMP                               NOT NULL,
    PRIMARY KEY (id)
);
