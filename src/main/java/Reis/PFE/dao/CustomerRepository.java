package Reis.PFE.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.transaction.Transactional;

import org.hibernate.sql.Delete;
import org.omg.CORBA.LongLongSeqHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import Reis.PFE.Entities.Customer;
@RepositoryRestResource
public interface CustomerRepository extends JpaRepository<Customer,Integer>{
/*
 * Extraction des duplications par NID et dates de naissances pour les personnes physiques.
 */
@Query(value="SELECT * FROM (SELECT * , count(*) OVER (PARTITION BY nid,birth_date,step_4,entity_type) AS count FROM customers_clean) tableWithCount  WHERE tableWithCount.count > 1 and entity_type='PP' and step_4 is null", nativeQuery=true)
public List<Customer> get_ducplicationByNidAndBirth();
/*
 * Extraction des duplications par NID pour les personnes morales.
 */
@Query(value="SELECT * FROM (SELECT * , count(*) OVER (PARTITION BY nid,step_5,entity_type) AS count FROM customers_clean) tableWithCount  WHERE tableWithCount.count > 1 and entity_type='PM' and step_5 is null ", nativeQuery=true)
public List<Customer> get_ducplicationByNidPM();
/*
 * Rechercher par NID
 */
@Query(value="SELECT  * from customers_clean where (step_1 is null and (birth_date !~ (?1) or birth_date is null))",nativeQuery=true)
public List<Customer>findErrBD(String birthDateRegex);
/*
 * Extraction des nationalités erronées.
 */
@Query(value="select * from customers_clean where step_2 is null and nationality not in('Tunisia','Algeria', 'Libya','Egypt','Marroco','France', 'Spain', 'Portugal','Canada', 'USA','Saudia Arabia', 'Qatar', 'Emirates')",nativeQuery=true)
public List<Customer> find_err_Nationalities();
/*
 * Extraction des numéros d'identification erronés.
 * Types d'identifications : CIN, Passeport
 */
@Query(value="select * from customers_clean where (tin='CIN' AND nationality='Tunisia' AND entity_type='PP' AND step_3 is null AND (nid is null or nid !~ ?1))",nativeQuery=true)
public List<Customer> getErrNID(String cinRegex);
@Query(value="select * from customers_clean where (tin='Passeport' and nationality!='Tunisia' and entity_type='PP' and step_3 is null and (nid !~ ?1 or nid is null))",nativeQuery=true)
public List<Customer> getERRPass(String passRegex);
@Query(value="select * from customers_clean where (tin='Carte sejour' and nationality!='Tunisia' and entity_type='PP' and step_3 is null and(nid !~ ?1 or nid is null))",nativeQuery=true)
public List<Customer> getERRCSejour(String carteSejourRegex);
@Query(value="select * from customers_clean where (entity_type='PM' AND step_3 is null AND (tin='Matricule fiscal' or tin='Identifiant unique') AND ((nid !~ '^[0-9]{6,7}[A-Z]{1}[A,B,D,N,P]{1}[M,N,C,P]{1}[0-9]{3}$' or nid !~ '^[0-9]{6,7}[A-Z]{1}$') or nid is null))",nativeQuery=true)
public List<Customer> getERRMatFisc(String matFiscRegex);
@Query(value="select * from customers_clean where (entity_type='PM' AND step_3 is null AND (tin='Registre commercial') AND ((nid !~ ?1) or nid is null))",nativeQuery=true)
public List<Customer> getERRRegComm(String RegCommRegex);
/*
 * Extraction des tins manquants
 */
@Query(value="select * from customers_clean where (tin is null or (tin not in ('Passeport','CIN','Identifiant unique','Matricule fiscal','Registre commercial','Carte sejour')))",nativeQuery = true)
public List<Customer> getERRTin();
/*
 * Extraction des téls erronés pour les personnes physiques
 */
@Query(value="select * from customers_clean where entity_type='PP' and ((tel1 is null and tel2 is null) or((tel1 is null and tel2 !~'^[2,9,3,5]{1}[0-9]{7}$') or (tel2 is null and tel1!~'^[2,9,3,5]{1}[0-9]{7}$' )))",nativeQuery = true)
public List<Customer> getERRTel();
/*
 * Extraction des faxs erronés pour les personnes morales
 */
@Query(value="select * from customers_clean where entity_type='PM' and ((fax1 is null and fax2 is null) or((fax1 is null and fax2 !~'^[7]{1}[0-9]{7}$') or (fax2 is null and fax1!~'^[7]{1}[0-9]{7}$' )))",nativeQuery = true)
public List<Customer> getERRFax();
/*
 * Extraction des cordonnées erronés.
 */
@Query(value="select * from customers_clean where (email !~ '(?:[a-z0-9!#$%&*+\\/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&*+\\/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])' or email is null)",nativeQuery=true)
public List<Customer> getERREmail(String emailRegex);
/*
 * Supprimer Par ID
 */
@Transactional
@Modifying
public void deleteById(long id);
/*uy
 * Rechercher par NID et Date de naissance
 */
public ArrayList<Customer>findByBirthdateAndNid(String Birth_date,String Nid);
/*
 * Rechercher par NID
 */
public List<Customer> findByNid(String Nid);
@Query(value="select * from customers_clean where id=?1",nativeQuery = true)
public Customer getCustomerById(int id);
}
