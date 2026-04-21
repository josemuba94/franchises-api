resource "random_password" "db_password" {
  length = 8
}

resource "aws_secretsmanager_secret" "franchises_db_password" {
  name = "franchises-db-password"
}

resource "aws_secretsmanager_secret_version" "franchises_db_password_version" {
  secret_id     = aws_secretsmanager_secret.franchises_db_password.id
  secret_string = random_password.db_password.result
}

resource "aws_db_instance" "franchises-db" {
  allocated_storage   = 15
  instance_class      = "db.t3.micro"
  engine              = "postgres"
  identifier          = "franchises-db-instance"
  db_name             = "postgres"
  username            = "postgres"
  password            = aws_secretsmanager_secret_version.franchises_db_password_version.secret_string
  publicly_accessible = true
  skip_final_snapshot = true
  tags = {
    Name        = "Franchises DB Instance"
    Environment = "Dev"
  }
  depends_on = [aws_secretsmanager_secret_version.franchises_db_password_version]
}