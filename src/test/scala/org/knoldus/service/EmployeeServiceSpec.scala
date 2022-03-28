package org.knoldus.service

import org.knoldus.dao.sql.EmployeeRepositoryImpl
import org.knoldus.db.DBConnection
import org.knoldus.model.Employee
import org.scalatest.BeforeAndAfterAll
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.{Millis, Seconds, Span}
import java.util.UUID
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.{Duration, DurationInt}

class EmployeeServiceSpec extends AnyFlatSpec with Matchers with BeforeAndAfterAll with ScalaFutures {

  val dbConnection = new DBConnection
  val employeeRepository = new EmployeeRepositoryImpl(dbConnection.db)
  val employeeService = new EmployeeService(employeeRepository)

  implicit val defaultPatience: PatienceConfig =
    PatienceConfig(timeout = Span(2, Seconds), interval = Span(20, Millis))

  override def beforeAll() = {
    Await.result(employeeRepository.init(), Duration.Inf)
  }

  override def afterAll() = {
    Await.result(employeeRepository.drop(), Duration.Inf)
  }

  behavior of "EmployeeService"

  it should "insert a new employee record in database" in {
    val id = Some(UUID.randomUUID().toString)
    val newEmployee = Employee(id, "Prateek", "prateekg@knoldus.com", "Scala")

    whenReady(employeeService.insertEmployee(newEmployee)) { insertEmployeeResult =>
      assert(insertEmployeeResult == 1)
    }
  }

  it should "get the employee record if the given employee id exist in database" in {
    val id = Some(UUID.randomUUID().toString)
    val newEmployee = Employee(id, "Prateek", "prateekg@knoldus.com", "Scala")

    whenReady(employeeService.insertEmployee(newEmployee)) { _ =>
      whenReady(employeeService.getEmployee(id)) { employee =>
        employee shouldEqual Some(newEmployee)
      }
    }
  }

  it should "not get the employee record if the given employee id does not exist in database" in {
    val id = Some(UUID.randomUUID().toString)
    whenReady(employeeService.getEmployee(id)) { employee =>
      employee shouldEqual None
    }
  }

  it should "update the employee corresponding to the given employee id if it exist in database" in {
    val id = Some(UUID.randomUUID().toString)
    val employee = Employee(id, "Prateek", "prateekg@knoldus.com", "Scala")
    val updatedEmployee = employee.copy(studio = "Java")

    whenReady(employeeService.insertEmployee(employee)) { _ =>
      whenReady(employeeService.updateEmployee(id, updatedEmployee)) { updateEmployeeResult =>
        assert(updateEmployeeResult == 1)
      }
    }
  }

  it should "not update the employee if the given employee id does not exist in database" in {
    val id = Some(UUID.randomUUID().toString)
    val updatedEmployee = Employee(id, "Prateek", "prateekg@knoldus.com", "Scala")

    whenReady(employeeService.updateEmployee(id, updatedEmployee)) { updateEmployeeResult =>
      assert(updateEmployeeResult == 0)
    }
  }

  it should "delete the employee corresponding to the given employee id if it exist in database" in {
    val id = Some(UUID.randomUUID().toString)
    val employee = Employee(id, "Prateek", "prateekg@knoldus.com", "Scala")

    whenReady(employeeService.insertEmployee(employee)) { _ =>
      whenReady(employeeService.deleteEmployee(id)) { deleteEmployeeResult =>
        assert(deleteEmployeeResult == 1)
      }
    }
  }

  it should "not delete the employee if the given employee id does not exist in database" in {
    val id = Some(UUID.randomUUID().toString)

    whenReady(employeeService.deleteEmployee(id)) { deleteEmployeeResult =>
      assert(deleteEmployeeResult == 0)
    }
  }

}
