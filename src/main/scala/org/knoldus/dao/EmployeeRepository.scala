package org.knoldus.dao

import org.knoldus.model.Employee
import scala.concurrent.Future

trait EmployeeRepository {
  def insert(employee: Employee): Future[Int]

  def get(id: Option[String]): Future[Option[Employee]]

  def update(id: Option[String], updatedEmp: Employee): Future[Int]

  def delete(id: Option[String]): Future[Int]
}
