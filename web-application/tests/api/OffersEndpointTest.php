<?php

require_once __DIR__ . '/TestLoginTrait.php';

class OffersEndpointTest extends \Codeception\TestCase\Test
{

    use TestLoginTrait;

    /**
     * @var \ApiTester
     */
    protected $tester;

    protected function _before()
    {
    }

    protected function _after()
    {
    }

    // tests
    public function testGetIndex()
    {
        $this->tester->haveHttpHeader('Content-type', 'application/json');
        $this->tester->sendGET('/api/offers/');
        $this->tester->seeResponseCodeIs(200);
        $this->tester->seeResponseIsJson();

        $this->tester->seeResponseJsonMatchesJsonPath('$.status');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.[0].id');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.[0].title');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.[0].description');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.[0].latitude');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.[0].longitude');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.[0].createdAt');
    }


    public function testDetails()
    {
        $token = $this->doLogin('refugee1@mailinator.com', '123456');

        $this->tester->haveHttpHeader('X-Api-Token', $token);
        $this->tester->haveHttpHeader('Content-type', 'application/json');
        $this->tester->sendGET('/api/offers/');
        $data = json_decode($this->tester->grabResponse(), true);

        $id = $data['data'][0]['id'];

        $this->tester->sendGET('/api/offers/' . $id);
        $this->tester->seeResponseCodeIs(200);
        $this->tester->seeResponseIsJson();

        $this->tester->seeResponseJsonMatchesJsonPath('$.status');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.id');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.title');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.description');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.latitude');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.longitude');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.createdAt');
    }

    public function testAccept()
    {
        $token = $this->doLogin('refugee1@mailinator.com', '123456');

        $this->tester->haveHttpHeader('X-Api-Token', $token);
        $this->tester->haveHttpHeader('Content-type', 'application/json');
        $this->tester->sendGET('/api/offers/');
        $data = json_decode($this->tester->grabResponse(), true);

        $id = $data['data'][0]['id'];

        $this->tester->haveHttpHeader('Content-type', 'application/json');
        $this->tester->sendPOST('/api/offers/' . $id . '/accept');
        $this->tester->seeResponseCodeIs(200);
        $this->tester->seeResponseIsJson();
        $data = json_decode($this->tester->grabResponse(), true);

        $this->assertEquals('ok', $data['status']);

        $this->tester->sendGET('/api/offers/' . $id);
        $this->tester->cantSeeResponseCodeIs(200);

    }
}