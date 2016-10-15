<?php

namespace ApiBundle\Controller;


use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use Zantolov\AppBundle\Controller\API\ApiController;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;


/**
 *
 * @Route("/api/refugees")
 */
class RefugeesApiController extends ApiController
{

    /**
     * @Route("/", name="refugees.index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $items = $this->getDoctrine()->getManager()->getRepository('AppBundle:Refugee')->findAll();
        return $this->createResponse([
            self::KEY_STATUS => self::STATUS_OK,
            self::KEY_DATA   => $items
        ]);
    }

    /**
     * @Route("/check/{id}", name="refugees.check")
     * @Method("GET")
     */
    public function checkAction(Request $request, $id)
    {
        $ref = $this->getDoctrine()->getManager()->getRepository('AppBundle:Refugee')->findOneBy(['id' => $id]);

        if (empty($ref)) {
            throw $this->createNotFoundException('Refugee not found with id ' . $id);
        }

        $lat = $request->get('lat');
        $lng = $request->get('lng');

        return $this->createResponse([
            self::KEY_STATUS => self::STATUS_OK,
            self::KEY_DATA   => [
                'url' => $this->generateUrl('site.profile.check', ['id' => $id], UrlGeneratorInterface::ABSOLUTE_URL)
            ]]);

    }

    /**
     *
     * @Route("/{id}", name="refugees.details")
     * @Method("GET")
     */
    public function detailsAction($id)
    {
        $ref = $this->getDoctrine()->getManager()->getRepository('AppBundle:Refugee')->findOneBy(['id' => $id]);

        if (empty($ref)) {
            throw $this->createNotFoundException('Refugee not found with id ' . $id);
        }

        return $this->createResponse([
            self::KEY_STATUS => self::STATUS_OK,
            self::KEY_DATA   => $ref
        ]);
    }


}