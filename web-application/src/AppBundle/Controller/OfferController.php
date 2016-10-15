<?php

namespace AppBundle\Controller;

use Zantolov\AppBundle\Controller\EntityCrudController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Template;
use AppBundle\Entity\Offer;
use AppBundle\Form\OfferType;

/**
 * Offer controller.
 *
 * @Route("/admin/offer")
 */
class OfferController extends EntityCrudController
{

    protected function getEntityClass()
    {
        return 'AppBundle:Offer';
    }

    /**
     * Lists all Offer entities.
     *
     * @Route("/", name="admin_offer.index")
     * @Method("GET")
     * @Template()
     */
    public function indexAction(Request $request)
    {

        return parent::baseIndexAction($request);
    }

    /**
     * Creates a new Offer entity.
     *
     * @Route("/", name="admin_offer.create")
     * @Method("POST")
     * @Template("AppBundle:Offer:new.html.twig")
     */
    public function createAction(Request $request)
    {
        return parent::baseCreateAction(
            $request,
            new Offer(),
            'admin_offer.show');


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
            new OfferType(),
            $this->generateUrl('admin_offer.create')
        );
    }

    /**
     * Displays a form to create a new Offer entity.
     *
     * @Route("/new", name="admin_offer.new")
     * @Method("GET")
     * @Template()
     */
    public function newAction()
    {

        return parent::baseNewAction(new Offer());
    }

    /**
     * Finds and displays a Offer entity.
     *
     * @Route("/{id}", name="admin_offer.show", requirements={"id"="\d+"})
     * @Method("GET")
     * @Template()
     */
    public function showAction($id)
    {

        return parent::baseShowAction($id);
    }

    /**
     * Displays a form to edit an existing Offer entity.
     *
     * @Route("/{id}/edit", name="admin_offer.edit")
     * @Method("GET")
     * @Template()
     */
    public function editAction($id)
    {

        return parent::baseEditAction($id);
    }

    /**
     * Creates a form to edit a Offer entity.
     *
     * @param Offer $entity The entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    protected function createEditForm($entity)
    {
        return parent::createBaseEditForm(
            $entity,
            new OfferType(),
            $this->generateUrl('admin_offer.update', array('id' => $entity->getId()))
        );
    }

    /**
     * Edits an existing Offer entity.
     *
     * @Route("/{id}", name="admin_offer.update")
     * @Method("PUT")
     * @Template("AppBundle:Offer:edit.html.twig")
     */
    public function updateAction(Request $request, $id)
    {

        return parent::baseUpdateAction(
            $request,
            $id,
            $this->generateUrl('admin_offer.edit', array('id' => $id))
        );
    }

    /**
     * Deletes a Offer entity.
     *
     * @Route("/{id}", name="admin_offer.delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, $id)
    {

        return parent::baseDeleteAction(
            $request,
            $id,
            $this->generateUrl('admin_offer.index')
        );
    }

    /**
     * Creates a form to delete a Offer entity by id.
     *
     * @param mixed $id The entity id
     *
     * @return \Symfony\Component\Form\Form The form
     */
    protected function createDeleteForm($id)
    {
        return parent::baseCreateDeleteForm(
            $this->generateUrl('admin_offer.delete', array('id' => $id))
        );
    }
}
