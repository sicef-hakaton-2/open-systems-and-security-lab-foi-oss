<?php

namespace AppBundle\Controller;

use Zantolov\AppBundle\Controller\EntityCrudController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Template;
use AppBundle\Entity\Refugee;
use AppBundle\Form\RefugeeType;

/**
 * Refugee controller.
 *
 * @Route("/admin/refugees")
 */
class RefugeeController extends EntityCrudController
{

    protected function getEntityClass()
    {
        return 'AppBundle:Refugee';
    }

    /**
     * Lists all Refugee entities.
     *
     * @Route("/", name="admin_refugees.index")
     * @Method("GET")
     * @Template()
     */
    public function indexAction(Request $request)
    {

        return parent::baseIndexAction($request);
    }

    /**
     * Creates a new Refugee entity.
     *
     * @Route("/", name="admin_refugees.create")
     * @Method("POST")
     * @Template("AppBundle:Refugee:new.html.twig")
     */
    public function createAction(Request $request)
    {
        return parent::baseCreateAction(
            $request,
            new Refugee(),
            'admin_refugees.show');


    }

    /**
     * Creates a form to create a Refugee entity.
     *
     * @param Refugee $entity The entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    protected function createCreateForm($entity)
    {
        return parent::createBaseCreateForm(
            $entity,
            new RefugeeType(),
            $this->generateUrl('admin_refugees.create')
        );
    }

    /**
     * Displays a form to create a new Refugee entity.
     *
     * @Route("/new", name="admin_refugees.new")
     * @Method("GET")
     * @Template()
     */
    public function newAction()
    {

        return parent::baseNewAction(new Refugee());
    }

    /**
     * Finds and displays a Refugee entity.
     *
     * @Route("/{id}", name="admin_refugees.show", requirements={"id"="\d+"})
     * @Method("GET")
     * @Template()
     */
    public function showAction($id)
    {

        return parent::baseShowAction($id);
    }

    /**
     * Displays a form to edit an existing Refugee entity.
     *
     * @Route("/{id}/edit", name="admin_refugees.edit")
     * @Method("GET")
     * @Template()
     */
    public function editAction($id)
    {

        return parent::baseEditAction($id) + ['refugeeId' => $id];
    }

    /**
     * Creates a form to edit a Refugee entity.
     *
     * @param Refugee $entity The entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    protected function createEditForm($entity)
    {
        return parent::createBaseEditForm(
            $entity,
            new RefugeeType(),
            $this->generateUrl('admin_refugees.update', array('id' => $entity->getId()))
        );
    }

    /**
     * Edits an existing Refugee entity.
     *
     * @Route("/{id}", name="admin_refugees.update")
     * @Method("PUT")
     * @Template("AppBundle:Refugee:edit.html.twig")
     */
    public function updateAction(Request $request, $id)
    {

        return parent::baseUpdateAction(
            $request,
            $id,
            $this->generateUrl('admin_refugees.edit', array('id' => $id))
        );
    }

    /**
     * Deletes a Refugee entity.
     *
     * @Route("/{id}", name="admin_refugees.delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, $id)
    {

        return parent::baseDeleteAction(
            $request,
            $id,
            $this->generateUrl('admin_refugees.index')
        );
    }

    /**
     * Creates a form to delete a Refugee entity by id.
     *
     * @param mixed $id The entity id
     *
     * @return \Symfony\Component\Form\Form The form
     */
    protected function createDeleteForm($id)
    {
        return parent::baseCreateDeleteForm(
            $this->generateUrl('admin_refugees.delete', array('id' => $id))
        );
    }
}
