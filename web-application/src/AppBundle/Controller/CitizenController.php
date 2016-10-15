<?php

namespace AppBundle\Controller;

use Zantolov\AppBundle\Controller\EntityCrudController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Template;
use AppBundle\Entity\Citizen;
use AppBundle\Form\CitizenType;

/**
 * Citizen controller.
 *
 * @Route("/admin/citizen")
 */
class CitizenController extends EntityCrudController
{

    protected function getEntityClass()
    {
        return 'AppBundle:Citizen';
    }

    /**
     * Lists all Citizen entities.
     *
     * @Route("/", name="admin_citizens.index")
     * @Method("GET")
     * @Template()
     */
    public function indexAction(Request $request)
    {

        return parent::baseIndexAction($request);
    }

    /**
     * Creates a new Citizen entity.
     *
     * @Route("/", name="admin_citizens.create")
     * @Method("POST")
     * @Template("AppBundle:Citizen:new.html.twig")
     */
    public function createAction(Request $request)
    {
        return parent::baseCreateAction(
            $request,
            new Citizen(),
            'admin_citizens.show');


    }

    /**
     * Creates a form to create a Citizen entity.
     *
     * @param Citizen $entity The entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    protected function createCreateForm($entity)
    {
        return parent::createBaseCreateForm(
            $entity,
            new CitizenType(),
            $this->generateUrl('admin_citizens.create')
        );
    }

    /**
     * Displays a form to create a new Citizen entity.
     *
     * @Route("/new", name="admin_citizens.new")
     * @Method("GET")
     * @Template()
     */
    public function newAction()
    {

        return parent::baseNewAction(new Citizen());
    }

    /**
     * Finds and displays a Citizen entity.
     *
     * @Route("/{id}", name="admin_citizens.show", requirements={"id"="\d+"})
     * @Method("GET")
     * @Template()
     */
    public function showAction($id)
    {

        return parent::baseShowAction($id);
    }

    /**
     * Displays a form to edit an existing Citizen entity.
     *
     * @Route("/{id}/edit", name="admin_citizens.edit")
     * @Method("GET")
     * @Template()
     */
    public function editAction($id)
    {

        return parent::baseEditAction($id);
    }

    /**
     * Creates a form to edit a Citizen entity.
     *
     * @param Citizen $entity The entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    protected function createEditForm($entity)
    {
        return parent::createBaseEditForm(
            $entity,
            new CitizenType(),
            $this->generateUrl('admin_citizens.update', array('id' => $entity->getId()))
        );
    }

    /**
     * Edits an existing Citizen entity.
     *
     * @Route("/{id}", name="admin_citizens.update")
     * @Method("PUT")
     * @Template("AppBundle:Citizen:edit.html.twig")
     */
    public function updateAction(Request $request, $id)
    {

        return parent::baseUpdateAction(
            $request,
            $id,
            $this->generateUrl('admin_citizens.edit', array('id' => $id))
        );
    }

    /**
     * Deletes a Citizen entity.
     *
     * @Route("/{id}", name="admin_citizens.delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, $id)
    {

        return parent::baseDeleteAction(
            $request,
            $id,
            $this->generateUrl('admin_citizens.index')
        );
    }

    /**
     * Creates a form to delete a Citizen entity by id.
     *
     * @param mixed $id The entity id
     *
     * @return \Symfony\Component\Form\Form The form
     */
    protected function createDeleteForm($id)
    {
        return parent::baseCreateDeleteForm(
            $this->generateUrl('admin_citizens.delete', array('id' => $id))
        );
    }
}
