output "db_instance_arn" {
  value = aws_db_instance.franchises-db.arn
}

output "db_instance_endpoint" {
  value = aws_db_instance.franchises-db.endpoint
}

output "db_instance_port" {
  value = aws_db_instance.franchises-db.port
}
