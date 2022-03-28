package org.knoldus.dao.sql

import org.knoldus.dao.EmployeeRepository
import org.knoldus.model.Employee
import slick.lifted.{ProvenShape, Rep, Tag}
import slick.jdbc.MySQLProfile.api._
import slick.jdbc.MySQLProfile
import scala.concurrent.{ExecutionContext, Future}

class EmployeeRepositoryImpl(db: MySQLProfile.backend.DatabaseDef)(implicit ec: ExecutionContext)
  extends TableQuery(new EmployeeTable(_))
    with EmployeeRepository {

  // creates the employee table in database
  def init() = db.run(this.schema.createIfNotExists)

  // drops the employee table from database
  def drop() = db.run(this.schema.dropIfExists)

  /** Below are the CRUD Operations
   * insert
   * get
   * update
   * delete
   */
  override def insert(employee: Employee): Future[Int] = {
    val query = this += employee
    db.run(query)
  }

  override def get(id: Option[String]): Future[Option[Employee]] = {
    val getQuery = this.filter(_.id === id).result.headOption
    db.run(getQuery)
  }

  override def update(id: Option[String], updatedEmp: Employee): Future[Int] = {
    val updateAction = this.filter(_.id === id).map(_.copy()).update(updatedEmp)
    db.run(updateAction)
  }

  override def delete(id: Option[String]): Future[Int] = {
    val deleteAction = this.filter(_.id === id).delete
    db.run(deleteAction)
  }

}

class EmployeeTable(tag: Tag) extends Table[Employee](tag, "Employees") {

  def id: Rep[String] = column[String]("id", O.PrimaryKey)

  def name: Rep[String] = column[String]("name")

  def email: Rep[String] = column[String]("email")

  def studio: Rep[String] = column[String]("studio")

  def * : ProvenShape[Employee] =
    (
      id.?,
      name,
      email,
      studio,
    ) <> (Employee.tupled, Employee.unapply)
}