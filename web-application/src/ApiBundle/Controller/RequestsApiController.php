<?php

namespace ApiBundle\Controller;


use AppBundle\Entity\Request;
use AppBundle\Service\RoleService;
use Symfony\Component\HttpFoundation\JsonResponse;
use Zantolov\AppBundle\Controller\API\ApiController;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Zantolov\AppBundle\Entity\User;


/**
 *
 * @Route("/api/requests")
 */
class RequestsApiController extends ApiController
{

    /**
     *
     * @Route("/", name="request.index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $items = $this->getDoctrine()->getManager()->getRepository('AppBundle:Request')->findBy([
            'citizen' => null,
            'active'  => true
        ]);

        return $this->createResponse([
            self::KEY_STATUS => self::STATUS_OK,
            self::KEY_DATA   => $items
        ]);
    }

    /**
     *
     * @Route("/{id}", name="requests.details")
     * @Method("GET")
     */
    public function detailsAction($id)
    {
        $ref = $this->getDoctrine()->getManager()->getRepository('AppBundle:Request')->findOneBy(['id' => $id]);

        if (empty($ref) || $ref->getActive() !== true) {
            throw $this->createNotFoundException('Request not found with id ' . $id);
        }

        return $this->createResponse([
            self::KEY_STATUS => self::STATUS_OK,
            self::KEY_DATA   => $ref
        ]);
    }

    /**
     * @Route("/{id}/accept", name="requests.accept")
     * @Method("POST")
     */
    public function acceptOffer($id)
    {
        $em = $this->getDoctrine()->getManager();
        $user = $this->getCurrentUser();

        if (empty($user) || !($user instanceof User)) {
            return $this->createResponse([
                self::KEY_STATUS  => self::STATUS_ERROR,
                self::KEY_MESSAGE => 'Please log in'
            ]);
        }

        /** @var Request $r */
        $r = $em->getRepository('AppBundle:Request')->find($id);

        if (empty($r) || $r->getActive() !== true) {
            return $this->createResponse([
                self::KEY_STATUS  => self::STATUS_ERROR,
                self::KEY_MESSAGE => 'Invalid Offer'
            ]);
        }

        /** @var RoleService $roleService */
        $roleService = $this->get('role_service');
        $profiles = $roleService->getUserRole($user);

        // Offer can be accepted only by refugee
        if (!isset($profiles['profile_citizen']) || empty($profiles['profile_citizen'])) {
            return $this->createResponse([
                self::KEY_STATUS  => self::STATUS_ERROR,
                self::KEY_MESSAGE => 'User is not citizen'
            ]);
        }

        $r->setCitizen($profiles['profile_citizen']);
        $r->setActive(false);
        $em->flush();

        return $this->createResponse([
            self::KEY_STATUS  => self::STATUS_OK,
            self::KEY_MESSAGE => 'Accepted'
        ]);
    }

}