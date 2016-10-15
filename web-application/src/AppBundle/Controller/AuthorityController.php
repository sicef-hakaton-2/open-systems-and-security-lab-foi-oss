<?php

namespace AppBundle\Controller;

use Zantolov\AppBundle\Controller\EntityCrudController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Template;
use AppBundle\Entity\Authority;
use AppBundle\Form\AuthorityType;

/**
 * Authority controller.
 *
 * @Route("/admin/authorities")
 */
class AuthorityController extends EntityCrudController
{

    protected function getEntityClass()
    {
        return 'AppBundle:Authority';
    }

    /**
     * Lists all Authority entities.
     *
     * @Route("/", name="admin_authorities.index")
     * @Method("GET")
     * @Template()
     */
    public function indexAction(Request $request)
    {

        return parent::baseIndexAction($request);
    }

    /**
     * Creates a new Authority entity.
     *
     * @Route("/", name="admin_authorities.create")
     * @Method("POST")
     * @Template("AppBundle:Authority:new.html.twig")
     */
    public function createAction(Request $request)
    {
        return parent::baseCreateAction(
            $request,
            new Authority(),
            'admin_authorities.show');


    }

    /**
     * Creates a form to create a Authority entity.
     *
     * @param Authority $entity The entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    protected function createCreateForm($entity)
    {
        return parent::createBaseCreateForm(
            $entity,
            new AuthorityType(),
            $this->generateUrl('admin_authorities.create')
        );
    }

    /**
     * Displays a form to create a new Authority entity.
     *
     * @Route("/new", name="admin_authorities.new")
     * @Method("GET")
     * @Template()
     */
    public function newAction()
    {

        return parent::baseNewAction(new Authority());
    }

    /**
     * Finds and displays a Authority entity.
     *
     * @Route("/{id}", name="admin_authorities.show", requirements={"id"="\d+"})
     * @Method("GET")
     * @Template()
     */
    public function showAction($id)
    {

        return parent::baseShowAction($id);
    }

    /**
     * Displays a form to edit an existing Authority entity.
     *
     * @Route("/{id}/edit", name="admin_authorities.edit")
     * @Method("GET")
     * @Template()
     */
    public function editAction($id)
    {

        return parent::baseEditAction($id);
    }

    /**
     * Creates a form to edit a Authority entity.
     *
     * @param Authority $entity The entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    protected function createEditForm($entity)
    {
        return parent::createBaseEditForm(
            $entity,
            new AuthorityType(),
            $this->generateUrl('admin_authorities.update', array('id' => $entity->getId()))
        );
    }

    /**
     * Edits an existing Authority entity.
     *
     * @Route("/{id}", name="admin_authorities.update")
     * @Method("PUT")
     * @Template("AppBundle:Authority:edit.html.twig")
     */
    public function updateAction(Request $request, $id)
    {

        return parent::baseUpdateAction(
            $request,
            $id,
            $this->generateUrl('admin_authorities.edit', array('id' => $id))
        );
    }

    /**
     * Deletes a Authority entity.
     *
     * @Route("/{id}", name="admin_authorities.delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, $id)
    {

        return parent::baseDeleteAction(
            $request,
            $id,
            $this->generateUrl('admin_authorities.index')
        );
    }

    /**
     * Creates a form to delete a Authority entity by id.
     *
     * @param mixed $id The entity id
     *
     * @return \Symfony\Component\Form\Form The form
     */
    protected function createDeleteForm($id)
    {
        return parent::baseCreateDeleteForm(
            $this->generateUrl('admin_authorities.delete', array('id' => $id))
        );
    }
}
