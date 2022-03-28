package org.knoldus.service

import org.knoldus.dao.EmployeeRepository
import org.knoldus.model.Employee

import scala.concurrent.Future

class EmployeeService(employeeRepository: EmployeeRepository) {

  def insertEmployee(employee: Employee): Future[Int] =
    employeeRepository.insert(employee)

  def getEmployee(id: Option[String]): Future[Option[Employee]] =
    employeeRepository.get(id)

  def updateEmployee(id: Option[String], updatedEmp: Employee): Future[Int] =
    employeeRepository.update(id, updatedEmp)

  def deleteEmployee(id: Option[String]): Future[Int] =
    employeeRepository.delete(id)
}
