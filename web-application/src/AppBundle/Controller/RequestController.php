<?php

namespace AppBundle\Controller;

use Zantolov\AppBundle\Controller\EntityCrudController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Template;
use AppBundle\Entity\Request as HelpRequest;
use AppBundle\Form\RequestType;

/**
 * Request controller.
 *
 * @Route("/admin/request")
 */
class RequestController extends EntityCrudController
{

    protected function getEntityClass()
    {
        return 'AppBundle:Request';
    }

    /**
     * Lists all Request entities.
     *
     * @Route("/", name="admin_request.index")
     * @Method("GET")
     * @Template()
     */
    public function indexAction(Request $request)
    {

        return parent::baseIndexAction($request);
    }

    /**
     * Creates a new Request entity.
     *
     * @Route("/", name="admin_request.create")
     * @Method("POST")
     * @Template("AppBundle:Request:new.html.twig")
     */
    public function createAction(Request $request)
    {
        return parent::baseCreateAction(
            $request,
            new HelpRequest(),
            'admin_request.show');


    }

    /**
     * Creates a form to create a Request entity.
     *
     * @param Request $entity The entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    protected function createCreateForm($entity)
    {
        return parent::createBaseCreateForm(
            $entity,
            new RequestType(),
            $this->generateUrl('admin_request.create')
        );
    }

    /**
     * Displays a form to create a new Request entity.
     *
     * @Route("/new", name="admin_request.new")
     * @Method("GET")
     * @Template()
     */
    public function newAction()
    {

        return parent::baseNewAction(new HelpRequest());
    }

    /**
     * Finds and displays a Request entity.
     *
     * @Route("/{id}", name="admin_request.show", requirements={"id"="\d+"})
     * @Method("GET")
     * @Template()
     */
    public function showAction($id)
    {

        return parent::baseShowAction($id);
    }

    /**
     * Displays a form to edit an existing Request entity.
     *
     * @Route("/{id}/edit", name="admin_request.edit")
     * @Method("GET")
     * @Template()
     */
    public function editAction($id)
    {

        return parent::baseEditAction($id);
    }

    /**
     * Creates a form to edit a Request entity.
     *
     * @param Request $entity The entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    protected function createEditForm($entity)
    {
        return parent::createBaseEditForm(
            $entity,
            new RequestType(),
            $this->generateUrl('admin_request.update', array('id' => $entity->getId()))
        );
    }

    /**
     * Edits an existing Request entity.
     *
     * @Route("/{id}", name="admin_request.update")
     * @Method("PUT")
     * @Template("AppBundle:Request:edit.html.twig")
     */
    public function updateAction(Request $request, $id)
    {

        return parent::baseUpdateAction(
            $request,
            $id,
            $this->generateUrl('admin_request.edit', array('id' => $id))
        );
    }

    /**
     * Deletes a Request entity.
     *
     * @Route("/{id}", name="admin_request.delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, $id)
    {

        return parent::baseDeleteAction(
            $request,
            $id,
            $this->generateUrl('admin_request.index')
        );
    }

    /**
     * Creates a form to delete a Request entity by id.
     *
     * @param mixed $id The entity id
     *
     * @return \Symfony\Component\Form\Form The form
     */
    protected function createDeleteForm($id)
    {
        return parent::baseCreateDeleteForm(
            $this->generateUrl('admin_request.delete', array('id' => $id))
        );
    }
}
